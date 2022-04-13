package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;;
import pl.polsl.management.api.controller.TopicApi;
import pl.polsl.management.api.model.TopicFindResponseModelApi;
import pl.polsl.management.api.model.TopicPostRequestModelApi;
import pl.polsl.management.api.model.TopicPostResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.mapper.TopicMapper;
import pl.polsl.projectmanagementsystem.model.Topic;
import pl.polsl.projectmanagementsystem.service.TopicService;

import javax.annotation.security.RolesAllowed;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TopicController implements TopicApi {

    private final TopicService topicService;
    private final TopicMapper topicMapper;

    @CrossOrigin
    @Override
    @RolesAllowed("lecturer")
    public ResponseEntity<TopicPostResponseModelApi> addNewTopic(TopicPostRequestModelApi topicRequestModelApi) {
        TopicDto result = topicService.addNewTopic(topicMapper.mapModelApiToDto(topicRequestModelApi));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

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

        FindResultDto<Topic> findResult= topicService.getAllTopics(searchDto);

        return new ResponseEntity<>(topicMapper.mapDtoToModelApi(findResult), HttpStatus.OK);
    }
}
