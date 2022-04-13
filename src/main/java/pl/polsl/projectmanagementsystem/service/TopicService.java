package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.mapper.TopicMapper;
import pl.polsl.projectmanagementsystem.model.Topic;
import pl.polsl.projectmanagementsystem.repository.TopicRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicDto addNewTopic(TopicDto topicDto){
       Topic topic = topicRepository.save(topicMapper.mapDtoToEntity(topicDto));

       return topicMapper.mapEntityToDto(topic);
    }
    public FindResultDto<Topic> getAllTopics(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage().intValue(), searchDto.getLimit().intValue());

        Page<Topic> topicList = topicRepository.findAll(pageRequest);

        return FindResultDto.<Topic>builder()
                .count((long) topicList.getNumberOfElements())
                .results(topicList.getContent())
                .startElement(pageRequest.getOffset())
                .totalCount(topicList.getTotalElements())
                .build();
    }
}
