package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.LecturerApi;
import pl.polsl.management.api.model.LecturerModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.LecturerDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.LecturerMapper;
import pl.polsl.projectmanagementsystem.mapper.UserMapper;
import pl.polsl.projectmanagementsystem.service.LecturerService;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
public class LecturerController implements LecturerApi {

    private final LecturerService lecturerService;
    private final UserMapper userMapper;
    private final LecturerMapper lecturerMapper;

    @Override
    @RolesAllowed("admin")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> createLecturer(LecturerModelApi userModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userModelApi);
        LecturerDto lecturerDto = lecturerMapper.mapModelApiToDto(userModelApi);

        lecturerService.createNewLecturer(userDto, lecturerDto);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
