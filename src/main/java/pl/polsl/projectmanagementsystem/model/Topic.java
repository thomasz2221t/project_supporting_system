package pl.polsl.projectmanagementsystem.model;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lecturer lecturer;

    @OneToMany(mappedBy = "topic")
    private List<Group> groups;

    private String topicName;

    @Column(length=3000)
    private String description;

    @ColumnDefault("true")
    private Boolean isActive;
}
