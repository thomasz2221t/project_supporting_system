package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.config.KeycloakConfig;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.exception.UsernameOrEmailTakenException;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.utils.ClientCredentials;

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

    private String addRoleToUser(UserDto userDto, UserRepresentation user) {
        UsersResource instance = KeycloakConfig.getInstance().realm("management").users();

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
}
