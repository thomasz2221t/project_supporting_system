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
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mark;

    @ManyToOne
    private Group group;

    @ManyToOne
    private Student student;

    @OneToMany(mappedBy="studentGroup")
    private List<Presence> presenceList;
}
