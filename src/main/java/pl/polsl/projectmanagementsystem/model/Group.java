package pl.polsl.projectmanagementsystem.model;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<StudentGroup> studentGroupList;


    @Column(name="filePaths")
    @ElementCollection(targetClass=String.class)
    private Set<String> filePaths = new HashSet<>();
}
