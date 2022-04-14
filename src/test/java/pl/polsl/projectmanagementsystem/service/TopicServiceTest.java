package pl.polsl.projectmanagementsystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.model.Topic;
import pl.polsl.projectmanagementsystem.repository.TopicRepository;
import pl.polsl.projectmanagementsystem.testHelpers.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class TopicServiceTest {

//    @Mock
//    private TopicRepository topicRepository;
//
//    @Test
//    void addNewTopic() {
//        // for
//        TopicDto topicDto = TopicDto.builder().topicName("name").description("description").build();
//
//        // mock
//        Mockito.when(topicRepository.save(Mockito.any()))
//                .thenReturn(Topic.builder().topicName("name").description("description").id(1L).build());
//
//        // when
//        TopicDto result = getTopicService().addNewTopic(topicDto);
//
//        // then
//        Assertions.assertEquals(1L,result.getId());
//        Assertions.assertEquals("name", result.getTopicName());
//        Assertions.assertEquals("description", result.getDescription());
//
//    }
//
//    private TopicService getTopicService() {
//        return new TopicService(topicRepository, Mappers.getTopicMapper(), null);
//    }

}