package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.config.KeycloakConfig;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;

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

        userDto.setUserId(userId);

        return userDto;
    }

    public UserDto deleteAdmin(String id) {
        UserRepresentation userRepresentation = keycloakService.deleteUser(id);

        return userMapper.mapModelApiToDto(userRepresentation);
    }

    public UserDto updateAdmin(UserDto userDto, String userId) {
        userDto.setRole("LECTURER");
        userDto.setUserId(userId);

        keycloakService.updateUser(userDto, userId);

        return userDto;
    }
}
