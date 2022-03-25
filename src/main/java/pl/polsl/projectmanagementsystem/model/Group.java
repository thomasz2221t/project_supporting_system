package pl.polsl.projectmanagementsystem.model;


import lombok.*;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GroupState groupState;

    @ManyToOne
    private Semester semester;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private Meeting meeting;

    @OneToMany(mappedBy = "group")
    private List<StudentGroup> studentGroupList;
}
