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
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "lecturer")
    private List<Topic> topics;

    private String firstName;
    private String lastName;
    private String subject;
    private String academicDegree;
    private String cathedral;
}
