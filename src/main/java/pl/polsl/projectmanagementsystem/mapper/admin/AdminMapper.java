package pl.polsl.projectmanagementsystem.mapper.admin;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.AdminResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.UserDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminMapper {

    @Mapping(target = "id", source = "userId")
    AdminResponseModelApi mapDtoToModelApi(UserDto userDto);
}
