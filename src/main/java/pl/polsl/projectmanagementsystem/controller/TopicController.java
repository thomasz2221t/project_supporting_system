package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.TopicApi;
import pl.polsl.management.api.model.TopicFindResponseModelApi;
import pl.polsl.management.api.model.TopicModelApi;
import pl.polsl.management.api.model.TopicRequestModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.mapper.TopicMapper;
import pl.polsl.projectmanagementsystem.mapper.topic.TopicFindResponseMapper;
import pl.polsl.projectmanagementsystem.service.TopicService;

import javax.annotation.security.RolesAllowed;

;


@RestController
@RequiredArgsConstructor
public class TopicController implements TopicApi {

    private final TopicService topicService;
    private final TopicFindResponseMapper topicFindResponseMapper;
    private final TopicMapper topicMapper;

    @CrossOrigin
    @Override
    @RolesAllowed("lecturer")
    public ResponseEntity<TopicModelApi> addNewTopic(TopicRequestModelApi topicRequestModelApi) {
        TopicDto result = topicService.addNewTopic(topicMapper.mapModelApiToDto(topicRequestModelApi));

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    @RolesAllowed("lecturer")
    public ResponseEntity<TopicModelApi> editTopic(Long id, TopicRequestModelApi topicRequestModelApi) {
        TopicDto result = topicService.editTopic(topicMapper.mapModelApiToDto(topicRequestModelApi), id);

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TopicModelApi> deleteTopic(Long id) {
        TopicDto result = topicService.deleteTopic(id);

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    @RolesAllowed({"user", "lecturer", "admin"})
    public ResponseEntity<TopicFindResponseModelApi> getAllTopics(Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<TopicDto> findResult = topicService.getAllTopics(searchDto);

        return new ResponseEntity<>(topicFindResponseMapper.mapDtoToModelApi(findResult), HttpStatus.OK);
    }
}
