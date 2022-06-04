package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.AdminApi;
import pl.polsl.management.api.model.*;
import pl.polsl.projectmanagementsystem.dto.*;
import pl.polsl.projectmanagementsystem.mapper.admin.AdminFindResponseMapper;
import pl.polsl.projectmanagementsystem.mapper.admin.AdminMapper;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.service.AdminService;
import pl.polsl.projectmanagementsystem.service.KeycloakService;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final UserMapper userMapper;
    private final AdminService adminService;
    private final KeycloakService keycloakService;
    private final AdminMapper adminMapper;
    private final AdminFindResponseMapper adminFindResponseMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> createAdmin(AdminModelApi adminModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(adminModelApi);

        userDto = adminService.createAdmin(userDto);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> deleteAdmin(String userId) {
        UserDto userDto = adminService.deleteAdmin(userId);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> updateAdmin(String userId, AdminUpdateModelApi adminUpdateModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(adminUpdateModelApi);

        userDto = adminService.updateAdmin(userDto, userId);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<AdminFindResponseModelApi> getAllAdmins(Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<UserDto> findResult = keycloakService.getAllAdmins(searchDto);

        return new ResponseEntity<>(adminFindResponseMapper.mapDtoToModelApi(findResult), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<AdminResponseModelApi> getAdminInfo() {
        UserDto userDto = adminService.getInfo();

        return new ResponseEntity<>(adminMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
