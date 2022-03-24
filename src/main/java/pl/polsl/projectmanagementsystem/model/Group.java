package pl.polsl.projectmanagementsystem.model;


import lombok.*;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;

import javax.persistence.*;

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
}
