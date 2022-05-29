package pl.polsl.projectmanagementsystem.dto;


import lombok.*;
import pl.polsl.projectmanagementsystem.model.Group;
import pl.polsl.projectmanagementsystem.model.Presence;
import pl.polsl.projectmanagementsystem.model.Student;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGroupDto {

    private Long id;
    private Long mark;
    private StudentDto student;
}
