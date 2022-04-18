package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.StudentApi;
import pl.polsl.management.api.model.StudentModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.StudentMapper;
import pl.polsl.projectmanagementsystem.mapper.UserMapper;
import pl.polsl.projectmanagementsystem.service.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final UserMapper userMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_user')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> createStudent(StudentModelApi studentModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(studentModelApi);
        StudentDto studentDto = studentMapper.mapModelApiToDto(studentModelApi);

        userDto = studentService.createStudent(userDto, studentDto);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
