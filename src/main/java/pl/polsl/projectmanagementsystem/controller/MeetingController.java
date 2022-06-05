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
import pl.polsl.projectmanagementsystem.dto.MeetingDto;
import pl.polsl.projectmanagementsystem.mapper.MeetingMapper;
import pl.polsl.projectmanagementsystem.service.MeetingService;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class MeetingController implements MeetingApi {

    private final MeetingService meetingService;
    private final MeetingMapper meetingMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<MeetingResponseModelApi> createMeeting(Long groupId, OffsetDateTime body) {
        MeetingDto meeting = meetingService.createMeeting(groupId, body);

        return new ResponseEntity<>(meetingMapper.mapDtoToModelApi(meeting), HttpStatus.OK);
    }
}
