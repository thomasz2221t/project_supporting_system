package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.GroupRequestModelApi;
import pl.polsl.management.api.model.GroupResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.model.Group;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GroupMapper {

    GroupDto mapModelApiToDto(GroupRequestModelApi groupRequestModelApi);
    Group mapDtoToEntity(GroupDto groupDto);
    GroupDto mapEntityToDto(Group group);
    GroupResponseModelApi mapDtoToModelApi(GroupDto groupDto);
}
