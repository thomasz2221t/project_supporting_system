package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.config.KeycloakConfig;
import pl.polsl.projectmanagementsystem.dto.*;
import pl.polsl.projectmanagementsystem.exception.UserNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.model.Student;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserMapper userMapper;
    private final KeycloakService keycloakService;

    public UserDto createAdmin(UserDto userDto) {
        userDto.setRole("ADMIN");

        String userId = keycloakService.addUser(userDto);

        userDto.setId(userId);

        return userDto;
    }

    public UserDto deleteAdmin(String id) {
        UserRepresentation userRepresentation = keycloakService.deleteUser(id);

        return userMapper.mapModelApiToDto(userRepresentation);
    }

    public UserDto updateAdmin(UserDto userDto, String userId) {
        userDto.setRole("ADMIN");
        userDto.setId(userId);

        keycloakService.updateUser(userDto, userId);

        return userDto;
    }

    public UserDto getInfo() {
        String userId = keycloakService.getUserId();

        UserRepresentation user = keycloakService.getUser(userId);

        return userMapper.mapModelApiToDto(user);
    }
}
