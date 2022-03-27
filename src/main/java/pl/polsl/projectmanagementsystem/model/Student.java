package pl.polsl.projectmanagementsystem.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {

    private String albumNo;

    private String firstname;
    private String lastname;

    private Date birthDate;

    @OneToMany(mappedBy = "student")
    private List<StudentGroup> studentGroupList;

    @OneToMany(mappedBy = "student")
    private List<StudentSemester> studentSemesterList;



}
