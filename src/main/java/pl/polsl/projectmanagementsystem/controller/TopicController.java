package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.TopicApi;
import pl.polsl.management.api.model.TopicFindResponseModelApi;
import pl.polsl.management.api.model.TopicModelApi;
import pl.polsl.management.api.model.TopicRequestModelApi;
import pl.polsl.management.api.model.TopicResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.mapper.topic.TopicMapper;
import pl.polsl.projectmanagementsystem.mapper.topic.TopicFindResponseMapper;
import pl.polsl.projectmanagementsystem.service.TopicService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TopicController implements TopicApi {

    private final TopicService topicService;
    private final TopicFindResponseMapper topicFindResponseMapper;
    private final TopicMapper topicMapper;

    @CrossOrigin
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    public ResponseEntity<TopicModelApi> addNewTopic(TopicRequestModelApi topicRequestModelApi) {
        TopicDto result = topicService.addNewTopic(topicMapper.mapModelApiToDto(topicRequestModelApi));

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    public ResponseEntity<TopicModelApi> editTopic(Long id, TopicRequestModelApi topicRequestModelApi) {
        TopicDto result = topicService.editTopic(topicMapper.mapModelApiToDto(topicRequestModelApi), id);

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    public ResponseEntity<TopicModelApi> deleteTopic(Long id) {
        TopicDto result = topicService.deleteTopic(id);

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    public ResponseEntity<TopicFindResponseModelApi> getAllTopics(Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<TopicDto> findResult = topicService.getAllTopics(searchDto);

        return new ResponseEntity<>(topicFindResponseMapper.mapDtoToModelApi(findResult), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    public ResponseEntity<TopicModelApi> getTopic(Long id) {
        TopicDto result = topicService.getTopic(id);

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    public ResponseEntity<List<TopicModelApi>> getAllTopicsByName(String lastname) {
        List<TopicModelApi> response = topicService.getTopicsByName(lastname).stream()
                .map(topicMapper::mapDtoToModelApi)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
