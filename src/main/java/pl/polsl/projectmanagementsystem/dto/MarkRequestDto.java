package pl.polsl.projectmanagementsystem.dto;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarkRequestDto {

    private String studentId;
    private Long mark;
}
