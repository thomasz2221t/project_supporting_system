package pl.polsl.projectmanagementsystem.dto;

import lombok.*;
import pl.polsl.projectmanagementsystem.dto.enums.GroupStateDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    private Long id;
    private GroupStateDto groupState;
    private Integer maxSize;
}
