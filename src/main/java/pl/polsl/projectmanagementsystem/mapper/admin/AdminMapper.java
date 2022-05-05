package pl.polsl.projectmanagementsystem.mapper.admin;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.AdminResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.UserDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminMapper {

    AdminResponseModelApi mapDtoToModelApi(UserDto userDto);
}
