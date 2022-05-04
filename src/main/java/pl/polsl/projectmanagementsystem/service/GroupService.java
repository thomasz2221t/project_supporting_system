package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
import pl.polsl.projectmanagementsystem.exception.GroupSizeException;
import pl.polsl.projectmanagementsystem.exception.SemesterNotFoundException;
import pl.polsl.projectmanagementsystem.exception.TopicNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.GroupMapper;
import pl.polsl.projectmanagementsystem.model.*;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;
import pl.polsl.projectmanagementsystem.repository.GroupRepository;
import pl.polsl.projectmanagementsystem.repository.SemesterRepository;
import pl.polsl.projectmanagementsystem.repository.StudentRepository;
import pl.polsl.projectmanagementsystem.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;
    private final GroupMapper groupMapper;
    private final SemesterRepository semesterRepository;

    public GroupDto createGroup(GroupDto groupDto, Long topicId, List<String> studentIds, Long semesterId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException("Topic not found"));
        Semester semester = semesterRepository.findById(semesterId).orElseThrow(() -> new SemesterNotFoundException("Semester not found"));

        Group group = groupMapper.mapDtoToEntity(groupDto);

        connectStudentsToGroup(groupDto, studentIds, group);

        if(group.getStudentGroupList().size() == group.getMaxSize()) {
            group.setGroupState(GroupState.FULL);
        }else {
            group.setGroupState(GroupState.REG);
        }
        group.setGroupState(GroupState.REG);
        group.setTopic(topic);
        group.setSemester(semester);

        return groupMapper.mapEntityToDto(groupRepository.save(group));
    }

    private void connectStudentsToGroup(GroupDto groupDto, List<String> studentIds, Group group) {
        if(studentIds != null) {
            if(studentIds.size() > groupDto.getMaxSize()) {
                throw new GroupSizeException("Group limit exceeded");
            }
            List<StudentGroup> studentGroupList = new ArrayList<>();

            List<Student> students = studentRepository.findAllByAlbumNoIn(studentIds);

            students.forEach(i ->{
                studentGroupList.add(StudentGroup.builder().mark(0l).group(group).student(i).build());
            });

            group.setStudentGroupList(studentGroupList);
        }
    }
}
