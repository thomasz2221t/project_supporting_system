package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.config.KeycloakConfig;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.utils.ClientCredentials;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakService {


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

        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = instance.get(userId);

        RoleRepresentation testerRealmRole = KeycloakConfig.getInstance()
                .realm("management").roles()//
                .get(userDto.getRole()).toRepresentation();

        userResource.roles().realmLevel()
                .add(Arrays.asList(testerRealmRole));

        return userId;
    }
}
