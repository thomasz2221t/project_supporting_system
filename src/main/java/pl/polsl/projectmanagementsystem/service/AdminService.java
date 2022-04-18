package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.UserDto;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final KeycloakService keycloakService;

    public UserDto createAdmin(UserDto userDto) {
        userDto.setRole("admin");

        String userId = keycloakService.addUser(userDto);

        userDto.setUserId(userId);

        return userDto;
    }
}
