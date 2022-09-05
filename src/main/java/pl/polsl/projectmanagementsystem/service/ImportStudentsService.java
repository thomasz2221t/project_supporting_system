package pl.polsl.projectmanagementsystem.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.ImportDto;
import pl.polsl.projectmanagementsystem.dto.SemesterDto;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.SemesterMapper;
import pl.polsl.projectmanagementsystem.mapper.student.StudentMapper;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.service.aws.AmazonClients;
import pl.polsl.projectmanagementsystem.utils.CloneUtils;
import pl.polsl.students.model.ImportStudentsApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportStudentsService {
    
    private final StudentService studentService;
    private final AmazonClients amazonClients;
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final SemesterMapper semesterMapper;

    @SneakyThrows
    public void importStudents(ImportDto importDto) {
        log.info("process=startImportMasterData");
        AmazonS3 amazonS3 = amazonClients.getAmazonS3();

        S3Object s3Object = amazonS3.getObject(importDto.getBucket(), importDto.getKey());

        ImportStudentsApi importProcess = CloneUtils.objectMapper()
                .readValue(s3Object.getObjectContent(), ImportStudentsApi.class);

        if (importProcess.getStudents() != null) {
            importProcess.getStudents()
                            .forEach(i -> {
                                        UserDto userDto = userMapper.mapImportModelToDto(i);
                                        StudentDto studentDto = studentMapper.mapModelApiToDto(i);
                                        List<SemesterDto> semesterDtos = i.getSemesters().stream()
                                                .map(semesterMapper::mapImportToDto)
                                                .collect(Collectors.toList());
                                        studentService.importStudent(userDto, studentDto, semesterDtos);
                                    }
                                    );
        }

        log.info("process=finishImportMasterData");
    }
}
