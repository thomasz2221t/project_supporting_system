package pl.polsl.projectmanagementsystem.dto;

import lombok.*;
import pl.polsl.projectmanagementsystem.dto.enums.GroupStateDto;
import pl.polsl.projectmanagementsystem.model.Meeting;
import pl.polsl.projectmanagementsystem.model.StudentGroup;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    private Long id;
    private TopicDto topic;
    private GroupStateDto groupState;
    private Integer maxSize;
    private List<StudentGroupDto> studentGroupList;
    private List<String> filePaths;
    private List<MeetingDto> meetings;
}
