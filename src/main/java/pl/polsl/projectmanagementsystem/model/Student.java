package pl.polsl.projectmanagementsystem.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;

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

    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentGroup> studentGroupList;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentSemester> studentSemesterList;

    @ColumnDefault("true")
    private Boolean isActive;
}
