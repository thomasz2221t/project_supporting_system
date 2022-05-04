package pl.polsl.projectmanagementsystem.mapper.student;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.StudentFindResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.StudentDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = StudentMapper.class)
public interface StudentFindResponseMapper {

    StudentFindResponseModelApi mapDtoToModelApi(FindResultDto<StudentDto> findResultDto);
}
