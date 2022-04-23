package pl.polsl.projectmanagementsystem.mapper.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.polsl.management.api.model.AdminModelApi;
import pl.polsl.management.api.model.LecturerModelApi;
import pl.polsl.management.api.model.StudentModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.LecturerDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.model.Lecturer;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(source = "realmRoles" , target = "role", qualifiedByName = "findUserRole")
    @Mapping(source = "id", target = "userId")
    UserDto mapModelApiToDto(UserRepresentation userRepresentation);

    UserResponseModelApi mapDtoToModelApi(UserDto userDto);
    UserDto mapModelApiToDto(LecturerModelApi lecturerModelApi);
    UserDto mapModelApiToDto(AdminModelApi adminModelApi);
    UserDto mapModelApiToDto(StudentModelApi studentModelApi);
    UserDto mapEntityToDto(Lecturer lecturer);

    @Named("findUserRole")
    static String findUserRole(List<String> roles) {
        return roles.stream()
                .filter(i -> i.equals("student") || i.equals("admin") || i.equals("lecturer")).findFirst()
                .orElse("user")
                .toUpperCase(Locale.ROOT);
    }
}
