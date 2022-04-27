package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.AdminApi;
import pl.polsl.management.api.model.AdminModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.service.AdminService;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final UserMapper userMapper;
    private final AdminService adminService;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_user')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> createAdmin(AdminModelApi adminModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(adminModelApi);

        userDto = adminService.createAdmin(userDto);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_user')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> deleteAdmin(String userId) {
        UserDto userDto = adminService.deleteAdmin(userId);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
