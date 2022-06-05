package pl.polsl.projectmanagementsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    @NotNull
    private OffsetDateTime date;

    @ManyToOne
    @NotNull
    private Group group;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Presence> presenceList;
}
