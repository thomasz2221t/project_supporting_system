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
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id")
public class Lecturer extends User{

    @OneToMany(mappedBy = "lecturer")
    private List<Topic> topics;

    private String firstName;
    private String lastName;
    private String subject;
    private String academicDegree;
    private String cathedral;
}
