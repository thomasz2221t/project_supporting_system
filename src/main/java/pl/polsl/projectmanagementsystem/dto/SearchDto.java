package pl.polsl.projectmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private Long limit;
    private Long page;
}
