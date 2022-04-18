package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.StudentModelApi;
import pl.polsl.projectmanagementsystem.dto.StudentDto;
import pl.polsl.projectmanagementsystem.model.Student;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = StudentSemesterMapper.class)
public interface StudentMapper {

    @Mapping(target = "studentSemesterList", source = "semesters")
    StudentDto mapModelApiToDto(StudentModelApi studentModelApi);

    @Mapping(target = "studentSemesterList", expression = "java(new ArrayList())")
    Student mapDtoToEntity(StudentDto studentDto);
}
