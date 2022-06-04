package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.GroupApi;
import pl.polsl.management.api.model.GroupFindResponseModelApi;
import pl.polsl.management.api.model.GroupResponseModelApi;

import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.mapper.group.GroupFindResponseMapper;
import pl.polsl.projectmanagementsystem.mapper.group.GroupMapper;
import pl.polsl.projectmanagementsystem.service.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController implements GroupApi {

    private final GroupService groupService;
    private final GroupMapper groupMapper;
    private final GroupFindResponseMapper groupFindResponseMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupResponseModelApi> addNewGroup(Long semesterId, Long topicId, Long maxSize) {
        GroupDto groupDto = groupService.createGroup(GroupDto.builder().maxSize(Math.toIntExact(maxSize)).build(), topicId, semesterId);

        return new ResponseEntity<>(groupMapper.mapDtoToModelApi(groupDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupResponseModelApi> insertStudents(Long groupId, List<String> studentIds) {
        GroupDto groupDto = groupService.insertStudents(groupId, studentIds);

        return new ResponseEntity<>(groupMapper.mapDtoToModelApi(groupDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupFindResponseModelApi> getSemesterGroups(Long semesterId, Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<GroupDto> findResult = groupService.getGroupsForSemester(searchDto, semesterId);

        return new ResponseEntity<>(groupFindResponseMapper.mapDtoToModelApi(findResult), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupResponseModelApi> signUpForGroup(Long groupId) {
        GroupDto groupDto = groupService.signUpForGroup(groupId);

        return new ResponseEntity<>(groupMapper.mapDtoToModelApi(groupDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupResponseModelApi> signUserForGroup(Long groupId, String userId) {
        GroupDto groupDto = groupService.singUserForGroup(groupId, userId);

        return new ResponseEntity<>(groupMapper.mapDtoToModelApi(groupDto), HttpStatus.OK);
    }
}
