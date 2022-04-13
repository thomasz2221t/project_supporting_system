package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.TopicApi;
import pl.polsl.management.api.model.TopicRequestModelApi;
import pl.polsl.management.api.model.TopicResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.mapper.TopicMapper;
import pl.polsl.projectmanagementsystem.service.TopicService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TopicController implements TopicApi {

    private final TopicService topicService;
    private final TopicMapper topicMapper;

    @CrossOrigin
    @Override
    @RolesAllowed("user")
    public ResponseEntity<TopicResponseModelApi> addNewTopic(TopicRequestModelApi topicRequestModelApi) {
        TopicDto result = topicService.addNewTopic(topicMapper.mapModelApiToDto(topicRequestModelApi));

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @CrossOrigin
    @Override
    public ResponseEntity<List<TopicResponseModelApi>> getAllTopics() {
        List<TopicDto> result = topicService.getAllTopics();

        return new ResponseEntity<>(result.stream().map(topicMapper::mapDtoToModelApi).collect(Collectors.toList()), HttpStatus.OK);
    }
}
