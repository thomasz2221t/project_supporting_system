package pl.polsl.projectmanagementsystem.mapper.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.polsl.management.api.model.*;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.model.Lecturer;
import pl.polsl.students.model.ImportStudentsApi;
import pl.polsl.students.model.StudentApi;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(source = "realmRoles" , target = "role", qualifiedByName = "findUserRole")
    @Mapping(source = "enabled", target = "isActive")
    UserDto mapModelApiToDto(UserRepresentation userRepresentation);

    UserResponseModelApi mapDtoToModelApi(UserDto userDto);
    UserDto mapModelApiToDto(LecturerModelApi lecturerModelApi);
    UserDto mapModelApiToDto(AdminModelApi adminModelApi);
    UserDto mapModelApiToDto(StudentModelApi studentModelApi);
    UserDto mapModelApiToDto(StudentApi importStudentsApi);
    UserDto mapEntityToDto(Lecturer lecturer);
    UserDto mapModelApiToDto(LecturerUpdateModelApi lecturerModelApi);
    UserDto mapModelApiToDto(StudentUpdateModelApi studentUpdateModelApi);
    UserDto mapModelApiToDto(AdminUpdateModelApi adminUpdateModelApi);
    UserDto mapImportModelToDto(StudentApi studentApi);


    @Named("findUserRole")
    static String findUserRole(List<String> roles) {
        if(roles == null) {
            return "USER";
        }

        return roles.stream()
                .filter(i -> i.equals("student") || i.equals("admin") || i.equals("lecturer")).findFirst()
                .orElse("user")
                .toUpperCase(Locale.ROOT);
    }
}
