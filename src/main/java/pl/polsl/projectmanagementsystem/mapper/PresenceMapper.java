package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.PresenceRequestModelApi;
import pl.polsl.management.api.model.PresenceResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.PresenceDto;
import pl.polsl.projectmanagementsystem.dto.PresenceRequestDto;
import pl.polsl.projectmanagementsystem.model.Presence;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = StudentGroupMapper.class)
public interface PresenceMapper {

    PresenceDto mapEntityToDto(Presence presence);

    @Mapping(target = "student", source = "studentGroup")
    PresenceResponseModelApi mapDtoToModelApi(PresenceDto presenceDto);

    PresenceRequestDto mapModelApiToDto(PresenceRequestModelApi presenceRequestModelApi);
}
