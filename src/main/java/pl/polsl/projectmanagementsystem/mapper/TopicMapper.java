package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.TopicFindResponseModelApi;
import pl.polsl.management.api.model.TopicPostRequestModelApi;
import pl.polsl.management.api.model.TopicPostResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.model.Topic;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TopicMapper {

    TopicDto mapModelApiToDto(TopicPostRequestModelApi topicRequestModelApi);
    TopicPostResponseModelApi mapDtoToModelApi(TopicDto topicDto);
    TopicFindResponseModelApi mapDtoToModelApi(FindResultDto findResultDto);
    Topic mapDtoToEntity(TopicDto topicDto);
    TopicDto mapEntityToDto(Topic topic);


}
