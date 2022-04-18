package pl.polsl.projectmanagementsystem.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fieldOfStudy;
    private Integer year;
    private Integer semester;

    @OneToMany(mappedBy = "semester")
    private List<Group> groups;

    @OneToMany(mappedBy = "semester")
    private List<StudentSemester> studentSemesterList;
}
