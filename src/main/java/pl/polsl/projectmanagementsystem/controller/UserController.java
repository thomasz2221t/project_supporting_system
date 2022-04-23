package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.UserApi;
import pl.polsl.management.api.model.UserFindResponseModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.user.UserFindResponseMapper;
import pl.polsl.projectmanagementsystem.service.KeycloakService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final KeycloakService keycloakService;
    private final UserFindResponseMapper userFindResponseMapper;

    @CrossOrigin
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_user')")
    public ResponseEntity<UserFindResponseModelApi> getAllUsers(Long page, Long limit) {
        FindResultDto<UserDto> users = keycloakService.getUsers(page, limit);

        return new ResponseEntity<>(userFindResponseMapper.mapDtoToModelApi(users), HttpStatus.OK);
    }
}
