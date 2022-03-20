package pl.polsl.projectmanagementsystem.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DbTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
