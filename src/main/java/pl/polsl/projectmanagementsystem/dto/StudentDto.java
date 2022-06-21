package pl.polsl.projectmanagementsystem.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String userId;
    private String albumNo;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private List<StudentSemesterDto> studentSemesterList;
    private Boolean isActive;
}
