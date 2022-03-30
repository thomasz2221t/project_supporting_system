package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.TopicApi;
import pl.polsl.management.api.model.TopicRequestModelApi;
import pl.polsl.management.api.model.TopicResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.mapper.TopicMapper;
import pl.polsl.projectmanagementsystem.service.TopicService;


@RestController
@RequiredArgsConstructor
public class TopicController implements TopicApi {

    private final TopicService topicService;
    private final TopicMapper topicMapper;

    @Override
    public ResponseEntity<TopicResponseModelApi> addNewTopic(TopicRequestModelApi topicRequestModelApi) {
        TopicDto result = topicService.addNewTopic(topicMapper.mapModelApiToDto(topicRequestModelApi));

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }
}
