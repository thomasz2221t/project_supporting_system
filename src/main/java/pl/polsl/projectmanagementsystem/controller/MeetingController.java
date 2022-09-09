package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.MeetingApi;
import pl.polsl.management.api.model.GroupResponseModelApi;
import pl.polsl.management.api.model.MeetingResponseModelApi;
import pl.polsl.management.api.model.PresenceRequestModelApi;
import pl.polsl.projectmanagementsystem.dto.MeetingDto;
import pl.polsl.projectmanagementsystem.dto.PresenceRequestDto;
import pl.polsl.projectmanagementsystem.mapper.MeetingMapper;
import pl.polsl.projectmanagementsystem.mapper.PresenceMapper;
import pl.polsl.projectmanagementsystem.service.MeetingService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MeetingController implements MeetingApi {

    private final MeetingService meetingService;
    private final MeetingMapper meetingMapper;
    private final PresenceMapper presenceMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<MeetingResponseModelApi> createMeeting(Long groupId, OffsetDateTime body) {
        MeetingDto meeting = meetingService.createMeeting(groupId, body);

        return new ResponseEntity<>(meetingMapper.mapDtoToModelApi(meeting), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<MeetingResponseModelApi> fillPresenceList(Long meetingId, List<PresenceRequestModelApi> presenceRequestModelApi) {
        List<PresenceRequestDto> presenceRequestDtos = presenceRequestModelApi.stream()
                .map(presenceMapper::mapModelApiToDto)
                .collect(Collectors.toList());

        MeetingDto meeting = meetingService.fillPresenceList(meetingId, presenceRequestDtos);

        return new ResponseEntity<>(meetingMapper.mapDtoToModelApi(meeting), HttpStatus.OK);
    }
}
