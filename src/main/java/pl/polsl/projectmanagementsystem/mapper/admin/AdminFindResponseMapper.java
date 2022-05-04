package pl.polsl.projectmanagementsystem.mapper.admin;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.AdminFindResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = AdminMapper.class)
public interface AdminFindResponseMapper {

    AdminFindResponseModelApi mapDtoToModelApi(FindResultDto<UserDto> findResultDto);
}
