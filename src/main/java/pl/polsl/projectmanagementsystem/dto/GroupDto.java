package pl.polsl.projectmanagementsystem.dto;

import lombok.*;
import pl.polsl.projectmanagementsystem.dto.enums.GroupStateDto;
import pl.polsl.projectmanagementsystem.model.StudentGroup;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    private Long id;
    private GroupStateDto groupState;
    private Integer maxSize;
    private List<StudentGroupDto> studentGroupList;
}
