package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.exception.*;
import pl.polsl.projectmanagementsystem.mapper.group.GroupMapper;
import pl.polsl.projectmanagementsystem.model.*;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;
import pl.polsl.projectmanagementsystem.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;
    private final GroupMapper groupMapper;
    private final SemesterRepository semesterRepository;
    private final StudentGroupRepository studentGroupRepository;

    @Transactional
    public GroupDto createGroup(GroupDto groupDto, Long topicId, Long semesterId) {
        Topic topic = topicRepository.findByIdAndIsActiveTrue(topicId).orElseThrow(() -> new TopicNotFoundException("Topic not found"));
        Semester semester = semesterRepository.findById(semesterId).orElseThrow(() -> new SemesterNotFoundException("Semester not found"));

        Group group = groupMapper.mapDtoToEntity(groupDto);

        group.setGroupState(GroupState.REG);
        group.setTopic(topic);
        group.setSemester(semester);
        group.setStudentGroupList(new ArrayList<>());

        return groupMapper.mapEntityToDto(groupRepository.save(group));
    }

    private void connectStudentsToGroup(List<String> studentIds, Group group) {
        if(studentIds != null) {
            if(studentIds.size() + group.getStudentGroupList().size() > group.getMaxSize()) {
                throw new GroupSizeException("Group limit exceeded");
            }

            List<StudentGroup> studentGroupList = new ArrayList<>();

            List<Student> students = studentRepository.findStudentsBySemester(studentIds, group.getSemester().getId());

            students.forEach(i -> studentGroupList.add(StudentGroup.builder().mark(0L).group(group).student(i).build()));

            group.setStudentGroupList(studentGroupList);
        }
    }

    @Transactional
    public GroupDto insertStudents(Long groupId, List<String> studentIds) {
        Group group = findGroupById(groupId);

        connectStudentsToGroup(studentIds, group);

        return groupMapper.mapEntityToDto(groupRepository.save(group));
    }

    public FindResultDto<GroupDto> getGroupsForSemester(SearchDto searchDto, Long semesterId) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage().intValue(), searchDto.getLimit().intValue());

        Page<Group> topicList = groupRepository.findGroupsBySemester(semesterId, pageRequest);

        return FindResultDto.<GroupDto>builder()
                .count((long) topicList.getNumberOfElements())
                .results(topicList.getContent().stream()
                        .map(groupMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .startElement(pageRequest.getOffset())
                .totalCount(topicList.getTotalElements())
                .build();
    }

    @Transactional
    public GroupDto signUpForGroup(Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Student student = studentRepository.findByUserId(currentPrincipalName).orElseThrow(() -> new UserNotFoundException("User not found"));
        Group group = findGroupById(groupId);

        List<StudentGroup> collect = student.getStudentGroupList().stream()
                .filter(i -> i.getGroup().getSemester().getId().equals(group.getSemester().getId()))
                .collect(Collectors.toList());

        collect.forEach(i -> studentGroupRepository.removePartsByIds(i.getId()));

        group.getStudentGroupList().add(StudentGroup.builder().mark(0L).group(group).student(student).build());

        return groupMapper.mapEntityToDto(group);
    }

    @Transactional
    public GroupDto singUserForGroup(Long groupId, String albumNo) {
        Student student = studentRepository.findById(albumNo).orElseThrow(() -> new UserNotFoundException("User not found"));
        Group group = findGroupById(groupId);

        List<StudentGroup> collect = student.getStudentGroupList().stream()
                .filter(i -> i.getGroup().getSemester().getId().equals(group.getSemester().getId()))
                .collect(Collectors.toList());

        collect.forEach(i -> studentGroupRepository.removePartsByIds(i.getId()));

        group.getStudentGroupList().add(StudentGroup.builder().mark(0L).group(group).student(student).build());

        return groupMapper.mapEntityToDto(group);
    }

    public Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group with given id not found"));
    }

    @Transactional
    public GroupDto changeGroupState(Long groupId, String body) {
        Group group = findGroupById(groupId);

        try {
            group.setGroupState(GroupState.valueOf(body));

        }catch (IllegalArgumentException illegalArgumentException){
            throw new WrongStateException("Wrong state");
        }

        return groupMapper.mapEntityToDto(group);
    }

    @Transactional
    public GroupDto getGroupInfo(Long groupId) {
        Group groupById = findGroupById(groupId);

        return groupMapper.mapEntityToDto(groupById);
    }
}
