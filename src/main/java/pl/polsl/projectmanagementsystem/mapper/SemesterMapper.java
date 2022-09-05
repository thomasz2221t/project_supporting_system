package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.SemesterModelApi;
import pl.polsl.management.api.model.SemesterResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.SemesterDto;
import pl.polsl.projectmanagementsystem.model.Semester;
import pl.polsl.students.model.SemesterApi;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SemesterMapper {

    SemesterDto mapModelApiToDto(SemesterModelApi semesterModelApi);
    SemesterDto mapImportToDto(SemesterApi semesterApi);
    SemesterDto mapEntityToDto(Semester semester);
    SemesterResponseModelApi mapDtoToModelApi(SemesterDto semesterDto);
}
