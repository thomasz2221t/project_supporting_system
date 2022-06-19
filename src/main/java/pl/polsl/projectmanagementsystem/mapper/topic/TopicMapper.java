package pl.polsl.projectmanagementsystem.mapper.topic;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.polsl.management.api.model.TopicModelApi;
import pl.polsl.management.api.model.TopicRequestModelApi;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.model.Group;
import pl.polsl.projectmanagementsystem.model.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TopicMapper {

    @Named("mapDtoToEntity")
    Topic mapDtoToEntity(TopicDto topicDto);
    TopicDto mapModelApiToDto(TopicRequestModelApi topicRequestModelApi);

    TopicModelApi mapDtoToModelApi(TopicDto topicDto);

    @Mapping(target = "groupIds", source = "groups", qualifiedByName = "getGroupIds")
    TopicDto mapEntityToDto(Topic topic);

    @Named("getGroupIds")
    default List<Long> getGroupIds(List<Group> groupDtos) {
        if(groupDtos == null) {
            return new ArrayList<>();
        }
        return groupDtos.stream()
                .map(Group::getId)
                .collect(Collectors.toList());
    }

    default Topic mapDtoToNewEntity(TopicDto topicDto) {
        Topic topic = mapDtoToEntity(topicDto);

        topic.setIsActive(true);

        return topic;
    }
}
