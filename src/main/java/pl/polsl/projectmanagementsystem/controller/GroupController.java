package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.management.api.controller.GroupApi;
import pl.polsl.management.api.model.*;

import pl.polsl.projectmanagementsystem.dto.*;
import pl.polsl.projectmanagementsystem.mapper.FileResponseMapper;
import pl.polsl.projectmanagementsystem.mapper.MarkRequestMapper;
import pl.polsl.projectmanagementsystem.mapper.group.GroupFindResponseMapper;
import pl.polsl.projectmanagementsystem.mapper.group.GroupMapper;
import pl.polsl.projectmanagementsystem.service.FileStorageService;
import pl.polsl.projectmanagementsystem.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GroupController implements GroupApi {

    private final GroupService groupService;
    private final GroupMapper groupMapper;
    private final GroupFindResponseMapper groupFindResponseMapper;
    private final FileStorageService fileStorageService;
    private final FileResponseMapper fileResponseMapper;
    private final MarkRequestMapper markRequestMapper;

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
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupFindResponseModelApi> getOpenSemesterGroups(Long semesterId, Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<GroupDto> findResult = groupService.getOpenGroupsForSemester(searchDto, semesterId);

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

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupResponseModelApi> changeGroupState(Long groupId, String body) {
        GroupDto groupDto = groupService.changeGroupState(groupId, body);

        return new ResponseEntity<>(groupMapper.mapDtoToModelApi(groupDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    @SneakyThrows
    public ResponseEntity<FileResponseModelApi> uploadFile(Long groupId, MultipartFile fileName) {
        FileResponseDto fileResponseDto = fileStorageService.storeFile(fileName.getOriginalFilename(),
                fileName.getInputStream(),
                groupId,
                fileName.getSize());


        return new ResponseEntity<>(fileResponseMapper.mapDtoToModelApi(fileResponseDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<Resource> downloadFile(Long groupId, String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName, groupId);

        // Try to determine file's content type
        String contentType = null;

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupModelApi> getGroupInfo(Long groupId) {
        GroupDto groupDto = groupService.getGroupInfo(groupId);

        return new ResponseEntity<>(groupMapper.mapDtoToCompleteModelApi(groupDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupModelApi> setStudentsMark(Long groupId, List<MarkRequestModelApi> markRequestModelApi) {
        List<MarkRequestDto> markRequestDtos = markRequestModelApi.stream()
                .map(markRequestMapper::mapModelApiToDto)
                .collect(Collectors.toList());

        GroupDto groupDto = groupService.setStudentsMark(groupId, markRequestDtos);

        return new ResponseEntity<>(groupMapper.mapDtoToCompleteModelApi(groupDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<GroupModelApi> generateFinalPdf(Long groupId) {
        GroupDto groupDto = groupService.generatePdf(groupId);

        return new ResponseEntity<>(groupMapper.mapDtoToCompleteModelApi(groupDto), HttpStatus.OK);
    }
}
