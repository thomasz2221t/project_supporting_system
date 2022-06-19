package pl.polsl.projectmanagementsystem.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

public class CloneUtils {

    private CloneUtils() {
        // private constructor for static methods
    }

    @SneakyThrows
    public static <T> T clone(T object, Class<T> valueType) {
        ObjectMapper objectMapper = objectMapper();

        return objectMapper.readValue(objectMapper.writeValueAsString(object), valueType);
    }

    public static ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new JavaTimeModule());
    }
}