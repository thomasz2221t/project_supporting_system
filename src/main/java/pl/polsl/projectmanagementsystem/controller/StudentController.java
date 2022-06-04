package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.StudentApi;
import pl.polsl.management.api.model.*;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.student.StudentFindResponseMapper;
import pl.polsl.projectmanagementsystem.mapper.student.StudentMapper;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.service.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final UserMapper userMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final StudentFindResponseMapper studentFindResponseMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<StudentResponseModelApi> createStudent(Long semesterId, StudentModelApi studentModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(studentModelApi);
        StudentDto studentDto = studentMapper.mapModelApiToDto(studentModelApi);

        studentDto = studentService.createStudent(userDto, studentDto, semesterId);

        return new ResponseEntity<>(studentMapper.mapDtoToModelApi(studentDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> deleteStudent(String id) {
        UserDto userDto = studentService.deleteStudent(id);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<StudentResponseModelApi> updateStudent(String id, StudentUpdateModelApi studentUpdateModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(studentUpdateModelApi);
        StudentDto studentDto = studentMapper.mapModelApiToDto(studentUpdateModelApi);

        studentDto = studentService.updateStudent(userDto, studentDto, id);

        return new ResponseEntity<>(studentMapper.mapDtoToModelApi(studentDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<StudentFindResponseModelApi> getStudentsForSemester(Long semesterId, Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<StudentDto> result = studentService.findStudentsBySemster(searchDto, semesterId);

        return new ResponseEntity<>(studentFindResponseMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<StudentFindResponseModelApi> getAllStudents(Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<StudentDto> result = studentService.findAllStudents(searchDto);

        return new ResponseEntity<>(studentFindResponseMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<StudentResponseModelApi> getStudentInfo() {
        StudentDto studentDto = studentService.getInfo();

        return new ResponseEntity<>(studentMapper.mapDtoToModelApi(studentDto), HttpStatus.OK);
    }
}
