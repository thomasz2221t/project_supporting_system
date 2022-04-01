package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.TopicRequestModelApi;
import pl.polsl.management.api.model.TopicResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.model.Topic;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TopicMapper {

    TopicDto mapModelApiToDto(TopicRequestModelApi topicRequestModelApi);
    TopicResponseModelApi mapDtoToModelApi(TopicDto topicDto);
    Topic mapDtoToEntity(TopicDto topicDto);
    TopicDto mapEntityToDto(Topic topic);

}
