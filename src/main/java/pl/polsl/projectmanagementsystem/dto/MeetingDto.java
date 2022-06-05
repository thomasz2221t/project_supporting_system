package pl.polsl.projectmanagementsystem.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingDto {

    private Long id;
    private OffsetDateTime date;
    private GroupDto group;
    private List<PresenceDto> presenceList;
}
