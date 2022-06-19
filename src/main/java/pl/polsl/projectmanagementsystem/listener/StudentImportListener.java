package pl.polsl.projectmanagementsystem.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.polsl.projectmanagementsystem.dto.ImportDto;
import pl.polsl.projectmanagementsystem.service.ImportStudentsService;
import pl.polsl.students.model.ImportStudentsNotificationApi;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentImportListener {

    private final ImportStudentsService importStudentsService;

    @SqsListener(value = "${management.queue}")
    public void queueListener(@Payload ImportStudentsNotificationApi importStudentsNotification, @Headers Map<String, String> headers) {
        ImportDto importDto = ImportDto.builder()
                .bucket(importStudentsNotification.getBucket())
                .key(importStudentsNotification.getExchangeFile()).build();

        importStudentsService.importStudents(importDto);
    }
}
