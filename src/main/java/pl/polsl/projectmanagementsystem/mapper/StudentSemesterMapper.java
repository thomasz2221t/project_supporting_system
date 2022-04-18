package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.SemesterModelApi;
import pl.polsl.projectmanagementsystem.dto.StudentSemesterDto;
import pl.polsl.projectmanagementsystem.model.StudentSemester;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = SemesterMapper.class)
public interface StudentSemesterMapper {

    @Mapping(target = "semester", source = "semesterModelApi")
    StudentSemesterDto mapModelApiToDto(SemesterModelApi semesterModelApi);

    StudentSemester mapDtoToModelApi(StudentSemesterDto studentSemesterDto);
}
