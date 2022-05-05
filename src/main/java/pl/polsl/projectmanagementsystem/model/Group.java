package pl.polsl.projectmanagementsystem.model;


import lombok.*;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private Integer maxSize;

    @ManyToOne
    private Semester semester;

    @ManyToOne
    private Topic topic;

    @OneToMany(mappedBy = "group")
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<StudentGroup> studentGroupList;
}