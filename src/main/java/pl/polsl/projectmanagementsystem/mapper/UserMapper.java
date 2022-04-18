package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.AdminModelApi;
import pl.polsl.management.api.model.LecturerModelApi;
import pl.polsl.management.api.model.StudentModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.LecturerDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.model.Lecturer;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserResponseModelApi mapDtoToModelApi(UserDto userDto);
    UserDto mapModelApiToDto(LecturerModelApi lecturerModelApi);
    UserDto mapModelApiToDto(AdminModelApi adminModelApi);
    UserDto mapModelApiToDto(StudentModelApi studentModelApi);
    UserDto mapEntityToDto(Lecturer lecturer);
}
