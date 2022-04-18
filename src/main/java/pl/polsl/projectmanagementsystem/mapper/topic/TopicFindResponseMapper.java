package pl.polsl.projectmanagementsystem.mapper.topic;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.TopicFindResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = TopicMapper.class)
public interface TopicFindResponseMapper {

    TopicFindResponseModelApi mapDtoToModelApi(FindResultDto<TopicDto> findResultDto);
}
