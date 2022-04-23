package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.GroupApi;
import pl.polsl.management.api.model.GroupRequestModelApi;
import pl.polsl.management.api.model.GroupResponseModelApi;

import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.mapper.GroupMapper;
import pl.polsl.projectmanagementsystem.service.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController implements GroupApi {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_user')")
    @CrossOrigin
    public ResponseEntity<GroupResponseModelApi> addNewGroup(Long topicId, List<String> studentIds, GroupRequestModelApi groupRequestModelApi) {
        GroupDto groupDto = groupService.createGroup(groupMapper.mapModelApiToDto(groupRequestModelApi), topicId, studentIds);

        return new ResponseEntity<>(groupMapper.mapDtoToModelApi(groupDto), HttpStatus.OK);
    }
}
