package pl.polsl.projectmanagementsystem.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDto {

    private Long id;
    private String fieldOfStudy;
    private Integer year;
    private Integer semester;
}
