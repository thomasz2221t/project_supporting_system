package pl.polsl.projectmanagementsystem.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long semesterNo;

    @ManyToOne
    private Semester semester;

    @ManyToOne
    private Student student;
}

