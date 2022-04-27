package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.GroupDto;
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

        if(studentIds != null) {
            List<StudentGroup> studentGroupList = new ArrayList<>();

            List<Student> students = studentRepository.findAllByAlbumNoIn(studentIds);

            students.forEach(i ->{
                studentGroupList.add(StudentGroup.builder().mark(0l).group(group).student(i).build());
            });

            group.setStudentGroupList(studentGroupList);
        }

        group.setGroupState(GroupState.REG);
        group.setTopic(topic);
        group.setSemester(semester);

        return groupMapper.mapEntityToDto(groupRepository.save(group));
    }
}
