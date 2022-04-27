package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.StudentModelApi;
import pl.polsl.management.api.model.StudentResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.model.Student;
import java.util.ArrayList;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = StudentSemesterMapper.class, imports = ArrayList.class )
public interface StudentMapper {

    StudentDto mapModelApiToDto(StudentModelApi studentModelApi);

    @Mapping(target = "studentSemesterList", expression = "java(new ArrayList())")
    Student mapDtoToEntity(StudentDto studentDto);

    StudentResponseModelApi mapDtoToModelApi(StudentDto studentDto);
    StudentDto mapEntityToDto(Student student);
}
