package pl.polsl.projectmanagementsystem.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.MeetingDto;
import pl.polsl.projectmanagementsystem.dto.PresenceRequestDto;
import pl.polsl.projectmanagementsystem.exception.GroupNotFoundException;
import pl.polsl.projectmanagementsystem.exception.MeetingNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.MeetingMapper;
import pl.polsl.projectmanagementsystem.model.*;
import pl.polsl.projectmanagementsystem.repository.GroupRepository;
import pl.polsl.projectmanagementsystem.repository.MeetingRepository;
import pl.polsl.projectmanagementsystem.repository.PresenceRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final PresenceRepository presenceRepository;
    private final GroupRepository groupRepository;
    private final MeetingMapper meetingMapper;

    @Transactional
    public MeetingDto createMeeting(Long groupId, OffsetDateTime body) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group with given id not found"));

        List<StudentGroup> studentGroupList = groupRepository.findStudentGroupList(groupId);

        Meeting meeting = Meeting.builder().group(group).date(body).build();

        List<Presence> presenceList = new ArrayList<>();

        studentGroupList.forEach(i ->
                presenceList.add(Presence.builder().wasPresent(false).meeting(meeting).studentGroup(i).build())
        );

        meeting.setPresenceList(presenceList);

        return meetingMapper.mapEntityToDto(meetingRepository.save(meeting));
    }

    @Transactional
    public MeetingDto fillPresenceList(Long meetingId, List<PresenceRequestDto> presenceRequestDtos) {
        Meeting meeting = findMeetingById(meetingId);

        List<Presence> presenceList = presenceRepository.findAllByIdIn(presenceRequestDtos.stream()
                .map(PresenceRequestDto::getPresenceId)
                .collect(Collectors.toList()));

        presenceList.forEach(
                i-> presenceRequestDtos.stream()
                        .filter(f -> f.getPresenceId().equals(i.getId()))
                        .findFirst()
                        .ifPresent(s -> i.setWasPresent(s.getWasPresent()))
        );

        return meetingMapper.mapEntityToDto(meetingRepository.save(meeting));
    }

    public Meeting findMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingNotFoundException("Meeting with given id not found"));
    }
}
