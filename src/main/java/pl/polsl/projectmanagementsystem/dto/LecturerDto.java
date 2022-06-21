package pl.polsl.projectmanagementsystem.dto;

import lombok.*;
import pl.polsl.projectmanagementsystem.model.Topic;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerDto {

    private Long id;

    private String userId;
    private List<TopicDto> topicDtos;
    private String firstName;
    private String lastName;
    private String subject;
    private String email;
    private String academicDegree;
    private String cathedral;
    private Boolean isActive;
}
