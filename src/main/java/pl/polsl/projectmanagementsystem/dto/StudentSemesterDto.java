package pl.polsl.projectmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentSemesterDto {

    private Long id;
    private SemesterDto semester;
}
