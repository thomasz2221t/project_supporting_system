package pl.polsl.projectmanagementsystem.testHelpers;

import pl.polsl.projectmanagementsystem.mapper.TopicMapper;
import pl.polsl.projectmanagementsystem.mapper.TopicMapperImpl;

public class Mappers {

    public static TopicMapper getTopicMapper() {
        return new TopicMapperImpl();
    }
}
