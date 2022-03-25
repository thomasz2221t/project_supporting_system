package pl.polsl.projectmanagementsystem.model;

import lombok.*;

import javax.persistence.*;
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

    private String firstname;
    private String lastname;

    private Date birthDate;

    @OneToMany(mappedBy = "student")
    private List<StudentGroup> studentGroupList;

    @OneToMany(mappedBy = "student")
    private List<StudentSemester> studentSemesterList;



}
