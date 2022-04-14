package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.TopicFindResponseModelApi;
import pl.polsl.management.api.model.TopicModelApi;
import pl.polsl.management.api.model.TopicRequestModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.model.Topic;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TopicMapper {

    Topic mapDtoToEntity(TopicDto topicDto);
    TopicDto mapModelApiToDto(TopicRequestModelApi topicRequestModelApi);
    TopicModelApi mapDtoToModelApi(TopicDto topicDto);

    @Mapping(target = "lecturerId", source = "lecturer.id")
    TopicDto mapEntityToDto(Topic topic);
}
