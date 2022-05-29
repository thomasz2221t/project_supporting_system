package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.FindResultDto;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.dto.SearchDto;
import pl.polsl.projectmanagementsystem.dto.TopicDto;
import pl.polsl.projectmanagementsystem.exception.GroupNotFoundException;
import pl.polsl.projectmanagementsystem.exception.GroupSizeException;
import pl.polsl.projectmanagementsystem.exception.SemesterNotFoundException;
import pl.polsl.projectmanagementsystem.exception.TopicNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.group.GroupMapper;
import pl.polsl.projectmanagementsystem.model.*;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;
import pl.polsl.projectmanagementsystem.repository.GroupRepository;
import pl.polsl.projectmanagementsystem.repository.SemesterRepository;
import pl.polsl.projectmanagementsystem.repository.StudentRepository;
import pl.polsl.projectmanagementsystem.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;
    private final GroupMapper groupMapper;
    private final SemesterRepository semesterRepository;

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
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group with given id not found"));

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
}
