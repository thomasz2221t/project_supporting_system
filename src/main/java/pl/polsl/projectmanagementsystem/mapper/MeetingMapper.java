package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.MeetingResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.MeetingDto;
import pl.polsl.projectmanagementsystem.mapper.group.GroupMapper;
import pl.polsl.projectmanagementsystem.model.Meeting;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {GroupMapper.class, PresenceMapper.class})
public interface MeetingMapper {

    MeetingDto mapEntityToDto(Meeting meeting);

    MeetingResponseModelApi mapDtoToModelApi(MeetingDto meetingDto);
}

