package pl.polsl.projectmanagementsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.polsl.projectmanagementsystem.exception.SemesterNotFoundException;
import pl.polsl.projectmanagementsystem.exception.TopicNotFoundException;
import pl.polsl.projectmanagementsystem.exception.UserNotFoundException;
import pl.polsl.projectmanagementsystem.exception.UsernameOrEmailTakenException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<Object> handeMissingTopic(TopicNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Topic does not exist";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handeMissingTopic(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "User not found";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(UsernameOrEmailTakenException.class)
    public ResponseEntity<Object> handleUserCreationException(UsernameOrEmailTakenException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Username or email already taken";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(SemesterNotFoundException.class)
    public ResponseEntity<Object> handleMissingSemester(SemesterNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Such a semester does not exist";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    private Map<String, Object> buildMessageBody(String msg) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", msg);

        return body;
    }
}
