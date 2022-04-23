package pl.polsl.projectmanagementsystem.mapper.user;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.UserFindResponseModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = UserMapper.class)
public interface UserFindResponseMapper {

    UserFindResponseModelApi mapDtoToModelApi(FindResultDto<UserDto> findResultDto);

}
