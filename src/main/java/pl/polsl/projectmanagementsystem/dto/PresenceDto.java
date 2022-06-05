package pl.polsl.projectmanagementsystem.dto;

import lombok.*;
import pl.polsl.projectmanagementsystem.model.Meeting;
import pl.polsl.projectmanagementsystem.model.StudentGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresenceDto {

    private Long id;
    private Boolean wasPresent = false;
    private Meeting meeting;
    private StudentGroupDto studentGroup;
}
