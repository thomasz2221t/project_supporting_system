package pl.polsl.projectmanagementsystem.mapper.student;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.StudentModelApi;
import pl.polsl.management.api.model.StudentResponseModelApi;
import pl.polsl.management.api.model.StudentUpdateModelApi;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.mapper.StudentSemesterMapper;
import pl.polsl.projectmanagementsystem.model.Student;
import java.util.ArrayList;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = StudentSemesterMapper.class, imports = ArrayList.class )
public interface StudentMapper {

    Student mapDtoToEntity(StudentDto studentDto);

    @Mapping(source = "id", target = "albumNo")
    StudentDto mapModelApiToDto(StudentModelApi studentModelApi);

    @Mapping(target = "id", source = "albumNo")
    StudentResponseModelApi mapDtoToModelApi(StudentDto studentDto);
    StudentDto mapEntityToDto(Student student);
    StudentDto mapModelApiToDto(StudentUpdateModelApi studentUpdateModelApi);

    default Student mapDtoToNewEntity(StudentDto studentDto) {
        Student student = mapDtoToEntity(studentDto);

        student.setStudentSemesterList(new ArrayList<>());
        student.setIsActive(true);

        return student;
    }
}
