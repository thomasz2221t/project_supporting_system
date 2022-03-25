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
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @OneToMany (mappedBy = "meeting")
    private List<Group> groupList;

    @OneToMany(mappedBy = "meeting")
    private List<Presence> presenceList;


}
