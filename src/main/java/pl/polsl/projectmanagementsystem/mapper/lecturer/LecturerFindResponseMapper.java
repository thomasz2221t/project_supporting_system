package pl.polsl.projectmanagementsystem.mapper.lecturer;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.LecturerFindResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.LecturerDto;
import pl.polsl.projectmanagementsystem.dto.StudentDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = LecturerMapper.class)
public interface LecturerFindResponseMapper {

    LecturerFindResponseModelApi mapDtoToModelApi(FindResultDto<LecturerDto> findResultDto);
}
