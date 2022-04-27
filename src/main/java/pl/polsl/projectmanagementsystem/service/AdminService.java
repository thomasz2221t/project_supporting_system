package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.exception.UserNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.model.Lecturer;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserMapper userMapper;
    private final KeycloakService keycloakService;

    public UserDto createAdmin(UserDto userDto) {
        userDto.setRole("ADMIN");

        String userId = keycloakService.addUser(userDto);

        userDto.setUserId(userId);
        userDto.setUserId(userId);

        return userDto;
    }

    public UserDto deleteAdmin(String id) {
        UserRepresentation userRepresentation = keycloakService.deleteUser(id);

        return userMapper.mapModelApiToDto(userRepresentation);
    }
}
