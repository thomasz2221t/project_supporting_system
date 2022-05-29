package pl.polsl.projectmanagementsystem.mapper.group;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.AdminFindResponseModelApi;
import pl.polsl.management.api.model.GroupFindResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.admin.AdminMapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = GroupMapper.class)
public interface GroupFindResponseMapper {

    GroupFindResponseModelApi mapDtoToModelApi(FindResultDto<GroupDto> findResultDto);
}
