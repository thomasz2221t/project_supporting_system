package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.LecturerModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.LecturerDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.model.Lecturer;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LecturerMapper {

    LecturerDto mapModelApiToDto(LecturerModelApi lecturerModelApi);
    Lecturer mapDtoToEntity(LecturerDto lecturerDto);
}
