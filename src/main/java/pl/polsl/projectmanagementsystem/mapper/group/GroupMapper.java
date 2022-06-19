package pl.polsl.projectmanagementsystem.mapper.group;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.polsl.management.api.model.GroupModelApi;
import pl.polsl.management.api.model.GroupResponseModelApi;
import pl.polsl.management.api.model.StudentResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.dto.StudentGroupDto;
import pl.polsl.projectmanagementsystem.mapper.MeetingMapper;
import pl.polsl.projectmanagementsystem.mapper.StudentGroupMapper;
import pl.polsl.projectmanagementsystem.mapper.lecturer.LecturerMapper;
import pl.polsl.projectmanagementsystem.mapper.student.StudentMapper;
import pl.polsl.projectmanagementsystem.mapper.topic.TopicMapper;
import pl.polsl.projectmanagementsystem.model.Group;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {StudentGroupMapper.class, MeetingMapper.class, TopicMapper.class, LecturerMapper.class})
public interface GroupMapper {

    @Mapping(target = "topic", source = "topic", qualifiedByName = "mapDtoToEntity")
    Group mapDtoToEntity(GroupDto groupDto);

    GroupDto mapEntityToDto(Group group);

    @Mapping(target = "topic", ignore = true)
    @Named(value = "mapEntityToDtoIgnoreTopic")
    GroupDto mapEntityToDtoIgnoreTopic(Group group);

    @Mapping(target = "students", source = "studentGroupList")
    GroupResponseModelApi mapDtoToModelApi(GroupDto groupDto);

    @Mapping(target = "students", source = "studentGroupList")
    GroupModelApi mapDtoToCompleteModelApi(GroupDto groupDto);
}
