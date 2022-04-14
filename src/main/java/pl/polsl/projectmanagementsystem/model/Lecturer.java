package pl.polsl.projectmanagementsystem.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    @OneToMany(mappedBy = "lecturer")
    private List<Topic> topics;

    private String firstName;
    private String lastName;
    private String subject;
    private String academicDegree;
    private String cathedral;

}
