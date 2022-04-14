package pl.polsl.projectmanagementsystem.dto;

import lombok.*;
import pl.polsl.projectmanagementsystem.model.Group;
import pl.polsl.projectmanagementsystem.model.Lecturer;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDto {

    private Long id;
    private String topicName;
    private String description;
    private Long lecturerId;
}
