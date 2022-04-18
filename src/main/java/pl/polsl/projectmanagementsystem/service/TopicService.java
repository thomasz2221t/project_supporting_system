package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.exception.TopicNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.topic.TopicMapper;
import pl.polsl.projectmanagementsystem.model.Lecturer;
import pl.polsl.projectmanagementsystem.model.Topic;
import pl.polsl.projectmanagementsystem.repository.LecturerRepository;
import pl.polsl.projectmanagementsystem.repository.TopicRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final LecturerRepository lecturerRepository;

    @Transactional
    public TopicDto addNewTopic(TopicDto topicDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Lecturer lecturer = lecturerRepository.findByUserId(currentPrincipalName);

        Topic topic = topicRepository.save(topicMapper.mapDtoToEntity(topicDto));
        topic.setLecturer(lecturer);

        return topicMapper.mapEntityToDto(topic);
    }

    @Transactional
    @SneakyThrows
    public TopicDto editTopic(TopicDto topicDto, Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException("Topic with given id doens't exist "));

        topic.setTopicName(topicDto.getTopicName());
        topic.setDescription(topicDto.getDescription());

        return topicMapper.mapEntityToDto(topic);
    }

    @Transactional
    @SneakyThrows
    public TopicDto deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException("Topic with given id doens't exist "));

        topicRepository.delete(topic);

        return topicMapper.mapEntityToDto(topic);
    }

    public FindResultDto<TopicDto> getAllTopics(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage().intValue(), searchDto.getLimit().intValue());

        Page<Topic> topicList = topicRepository.findAll(pageRequest);

        return FindResultDto.<TopicDto>builder()
                .count((long) topicList.getNumberOfElements())
                .results(topicList.getContent().stream()
                        .map(topicMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .startElement(pageRequest.getOffset())
                .totalCount(topicList.getTotalElements())
                .build();
    }
}
