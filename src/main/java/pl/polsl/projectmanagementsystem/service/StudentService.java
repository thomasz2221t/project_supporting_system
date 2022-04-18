package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.SemesterDto;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.exception.SemesterNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.StudentMapper;
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

    @Transactional
    public UserDto createStudent(UserDto userDto, StudentDto studentDto) {
        Student student = studentMapper.mapDtoToEntity(studentDto);

        studentDto.getStudentSemesterList().stream()
                .forEach(sl -> {
                    SemesterDto semesterDto = sl.getSemester();

                    Semester semester = semesterRepository.findByFieldOfStudyAndYearAndSemester(semesterDto.getFieldOfStudy(), semesterDto.getYear(), semesterDto.getSemester())
                            .orElseThrow(() -> new SemesterNotFoundException("No such semester found"));

                    StudentSemester studentSemester = StudentSemester.builder()
                            .student(student)
                            .semester(semester)
                            .build();

                    student.getStudentSemesterList().add(studentSemester);
                });

        studentRepository.save(student);

        userDto.setRole("student");

        String s = keycloakService.addUser(userDto);

        userDto.setUserId(s);

        return userDto;
    }

    @PostConstruct
    public void inserTestData() {
        Semester semester = Semester.builder().fieldOfStudy("infa").semester(1).year(1).build();
        semesterRepository.save(semester);
    }
}
