package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.config.KeycloakConfig;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.exception.UserNotFoundException;
import pl.polsl.projectmanagementsystem.exception.UsernameOrEmailTakenException;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.utils.ClientCredentials;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final UserMapper userMapper;

    public String addUser(UserDto userDto) {
        CredentialRepresentation credential = ClientCredentials
                .createPasswordCredentials(userDto.getPassword());

        UserRepresentation user = new UserRepresentation();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        return addRoleToUser(userDto, user);
    }

    public void updateUser(UserDto userDto, String userId) {
        UserRepresentation user = new UserRepresentation();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEnabled(true);

        UsersResource instance = KeycloakConfig.getInstance()
                .realm("management")
                .users();

        try {
            instance.get(userId).update(user);
        } catch (NotFoundException e){
            throw new UserNotFoundException("User not found");
        }
    }

    private String addRoleToUser(UserDto userDto, UserRepresentation user) {
        UsersResource instance = KeycloakConfig.getInstance()
                .realm("management")
                .users();

        Response response = instance.create(user);

        if(response.getStatusInfo().equals(Response.Status.CONFLICT))
            throw new UsernameOrEmailTakenException("Username or email taken");

        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = instance.get(userId);

        RoleRepresentation testerRealmRole = KeycloakConfig.getInstance()
                .realm("management").roles()//
                .get(userDto.getRole().toLowerCase(Locale.ROOT)).toRepresentation();

        userResource.roles().realmLevel()
                .add(Arrays.asList(testerRealmRole));

        return userId;
    }

    public FindResultDto<UserDto> getUsers(Long page, Long limit) {
        UsersResource instance = KeycloakConfig.getInstance().realm("management").users();

        Long count = Long.valueOf(instance.count());

        List<UserRepresentation> userRepresentations = instance.list(page.intValue() * limit.intValue(), limit.intValue());

        userRepresentations.stream().forEach(i -> i.setRealmRoles(
                KeycloakConfig.getInstance().realm("management").users()
                        .get(i.getId()).roles().realmLevel().listAll()
                        .stream()
                        .map(RoleRepresentation::getName).collect(Collectors.toList())));

        return FindResultDto.<UserDto>builder()
                .count((long) userRepresentations.size())
                .results(userRepresentations.stream()
                        .map(userMapper::mapModelApiToDto)
                        .collect(Collectors.toList()))
                .startElement(((long) page.intValue() * limit.intValue()))
                .totalCount(count)
                .build();
    }

    String getUserId() {
        Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> attributes;

        attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();

        String userId = (String)attributes.get("sub");

        if(userId == null) {
            throw new UserNotFoundException("User not found");
        }

        return userId;
    }

    public UserRepresentation deleteUser(String userId) {
        UserRepresentation user = getUser(userId);

        UsersResource instance = KeycloakConfig.getInstance()
                .realm("management")
                .users();

        user.setRealmRoles(KeycloakConfig.getInstance().realm("management").users()
                .get(userId).roles().realmLevel().listAll()
                .stream()
                .map(RoleRepresentation::getName).collect(Collectors.toList()));

        user.setEnabled(false);

        instance.get(userId).update(user);

        return user;
    }

    public UserRepresentation getUser(String userId) {
        UsersResource instance = KeycloakConfig.getInstance().realm("management").users();

        try {
            return instance.get(userId).toRepresentation();
        }catch (Exception e){
            throw new UserNotFoundException("User not found");
        }
    }

    public FindResultDto<UserDto> getAllAdmins(SearchDto searchDto) {
        RoleResource roleResource = KeycloakConfig.getInstance()
                .realm("management")
                .roles()
                .get("admin");

        int count = roleResource.getRoleUserMembers().size();

        Set<UserRepresentation> users = roleResource.getRoleUserMembers(searchDto.getPage().intValue() * searchDto.getLimit().intValue(), searchDto.getLimit().intValue());

        return FindResultDto.<UserDto>builder()
                .count((long) users.size())
                .results(users.stream()
                        .map(userMapper::mapModelApiToDto)
                        .collect(Collectors.toList()))
                .startElement(((long) searchDto.getPage().intValue() * searchDto.getLimit().intValue()))
                .totalCount((long) count)
                .build();
    }
}
