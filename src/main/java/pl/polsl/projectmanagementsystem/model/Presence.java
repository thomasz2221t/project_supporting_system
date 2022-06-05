package pl.polsl.projectmanagementsystem.model;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean wasPresent = false;

    @ManyToOne
    private Meeting meeting;

    @ManyToOne
    private StudentGroup studentGroup;




}
