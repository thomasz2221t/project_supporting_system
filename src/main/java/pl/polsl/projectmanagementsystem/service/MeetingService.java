package pl.polsl.projectmanagementsystem.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.MeetingDto;
import pl.polsl.projectmanagementsystem.exception.GroupNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.MeetingMapper;
import pl.polsl.projectmanagementsystem.model.*;
import pl.polsl.projectmanagementsystem.repository.GroupRepository;
import pl.polsl.projectmanagementsystem.repository.MeetingRepository;
import pl.polsl.projectmanagementsystem.repository.PresenceRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

        List<Presence> presenceList = new ArrayList<>();

        studentGroupList.forEach(i ->
                presenceList.add(Presence.builder().wasPresent(false).studentGroup(i).build())
        );

        Meeting meeting = Meeting.builder().group(group).presenceList(presenceList).date(body).build();

        return meetingMapper.mapEntityToDto(meetingRepository.save(meeting));
    }
}
