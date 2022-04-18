package pl.polsl.projectmanagementsystem.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private String albumNo;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "student")
    private List<StudentGroup> studentGroupList;

    @OneToMany(mappedBy = "student", cascade = CascadeType.MERGE)
    private List<StudentSemester> studentSemesterList;
}
