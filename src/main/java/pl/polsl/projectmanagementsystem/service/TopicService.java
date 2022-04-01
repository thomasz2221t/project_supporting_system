package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public List<TopicDto> getAllTopics() {
        List<Topic> topicList = topicRepository.findAll();
        return topicList.stream()
                .map(topicMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
