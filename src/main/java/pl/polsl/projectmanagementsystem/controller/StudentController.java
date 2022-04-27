package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.StudentApi;
import pl.polsl.management.api.model.StudentModelApi;
import pl.polsl.management.api.model.StudentResponseModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.StudentMapper;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
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
    public ResponseEntity<StudentResponseModelApi> createStudent(Long semesterId, StudentModelApi studentModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(studentModelApi);
        StudentDto studentDto = studentMapper.mapModelApiToDto(studentModelApi);

        studentDto = studentService.createStudent(userDto, studentDto, semesterId);

        return new ResponseEntity<>(studentMapper.mapDtoToModelApi(studentDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_user')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> deleteStudent(String userId) {
        UserDto userDto = studentService.deleteStudent(userId);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
