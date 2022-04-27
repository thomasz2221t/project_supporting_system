package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.exception.SemesterNotFoundException;
import pl.polsl.projectmanagementsystem.exception.UserNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.StudentMapper;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.model.Semester;
import pl.polsl.projectmanagementsystem.model.Student;
import pl.polsl.projectmanagementsystem.model.StudentSemester;
import pl.polsl.projectmanagementsystem.repository.SemesterRepository;
import pl.polsl.projectmanagementsystem.repository.StudentRepository;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final StudentMapper studentMapper;
    private final KeycloakService keycloakService;
    private final UserMapper userMapper;

    @Transactional
    public StudentDto createStudent(UserDto userDto, StudentDto studentDto, Long semesterId) {
        Student student = studentMapper.mapDtoToEntity(studentDto);

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new SemesterNotFoundException("No such semester found"));

        StudentSemester studentSemester = StudentSemester.builder()
                .student(student)
                .semester(semester)
                .build();

        student.getStudentSemesterList().add(studentSemester);

        userDto.setRole("STUDENT");

        String s = keycloakService.addUser(userDto);
        student.setUserId(s);

        return studentMapper.mapEntityToDto(studentRepository.save(student));
    }

    @PostConstruct
    public void inserTestData() {
        Semester semester = Semester.builder().fieldOfStudy("infa").semester(1).year(1).build();
        semesterRepository.save(semester);
    }

    public UserDto deleteStudent(String userId) {
        Student student = studentRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("Student not found"));

        UserRepresentation userRepresentation = keycloakService.deleteUser(student.getUserId());

        studentRepository.delete(student);

        return userMapper.mapModelApiToDto(userRepresentation);
    }
}
