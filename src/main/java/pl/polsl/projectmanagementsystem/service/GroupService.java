package pl.polsl.projectmanagementsystem.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.*;
import pl.polsl.projectmanagementsystem.dto.enums.GroupStateDto;
import pl.polsl.projectmanagementsystem.exception.*;
import pl.polsl.projectmanagementsystem.mapper.group.GroupMapper;
import pl.polsl.projectmanagementsystem.model.*;
import pl.polsl.projectmanagementsystem.model.enums.GroupState;
import pl.polsl.projectmanagementsystem.repository.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;
    private final GroupMapper groupMapper;
    private final SemesterRepository semesterRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final FileStorageService fileStorageService;

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

    public FindResultDto<GroupDto> getOpenGroupsForSemester(SearchDto searchDto, Long semesterId) {
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

        validateGroup(group, student.getStudentSemesterList());

        List<StudentGroup> collect = student.getStudentGroupList().stream()
                .filter(i -> i.getGroup().getSemester().getId().equals(group.getSemester().getId()))
                .collect(Collectors.toList());


        StudentGroup studentGroup = StudentGroup.builder().mark(0L).group(group).student(student).build();

        group.getStudentGroupList().add(studentGroup);

        group.getMeetings().forEach(
                m -> m.getPresenceList().add(Presence.builder().wasPresent(false).meeting(m).studentGroup(studentGroup).build())
        );

        collect.forEach(i -> studentGroupRepository.removePartsByIds(i.getId()));

        return groupMapper.mapEntityToDto(group);
    }

    @Transactional
    public GroupDto singUserForGroup(Long groupId, String albumNo) {
        Student student = studentRepository.findById(albumNo).orElseThrow(() -> new UserNotFoundException("User not found"));
        Group group = findGroupById(groupId);

        validateGroup(group, student.getStudentSemesterList());

        List<StudentGroup> collect = student.getStudentGroupList().stream()
                .filter(i -> i.getGroup().getSemester().getId().equals(group.getSemester().getId()))
                .collect(Collectors.toList());


        StudentGroup studentGroup = StudentGroup.builder().mark(0L).group(group).student(student).build();

        group.getStudentGroupList().add(studentGroup);

        group.getMeetings().forEach(
                m -> m.getPresenceList().add(Presence.builder().wasPresent(false).meeting(m).studentGroup(studentGroup).build())
        );

        collect.forEach(i -> studentGroupRepository.removePartsByIds(i.getId()));

        return groupMapper.mapEntityToDto(group);
    }

    private void validateGroup(Group group, List<StudentSemester> semester) {
        if (group.getMaxSize() <= group.getStudentGroupList().size()) {
            throw new GroupSizeException("group limit exceeded");
        }

        if(!group.getGroupState().equals(GroupState.OPEN)) {
            throw new GroupInWrongStateException("group in wrong state");
        }

        if(!semester.stream()
                .map(i-> i.getSemester().getId())
                .collect(Collectors.toList())
                .contains(group.getSemester().getId())) {

            throw new UserNotInSemesterException("User not part of semester");
        }
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

    @Transactional
    public GroupDto setStudentsMark(Long groupId, List<MarkRequestDto> markRequestDtos) {
        Group groupById = findGroupById(groupId);

        if(!groupById.getGroupState().equals(GroupState.OPEN)) {
            throw new GroupInWrongStateException("group in wrong state");
        }

        groupById.getStudentGroupList()
                .forEach(i-> markRequestDtos.stream()
                        .filter(k -> k.getStudentId().equals(i.getStudent().getAlbumNo()))
                        .findFirst()
                        .ifPresent(c -> i.setMark(c.getMark())));

        return groupMapper.mapEntityToDto(groupById);
    }

    @SneakyThrows
    @Transactional
    public GroupDto generatePdf(Long groupId) {
        GroupDto groupDto = groupMapper.mapEntityToDto(findGroupById(groupId));

        if(!groupDto.getGroupState().equals(GroupStateDto.CLOSE))
            throw new GroupInWrongStateException("group in wrong state");

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("finalResults.pdf"));

        Path path = Paths.get(ClassLoader.getSystemResource("politechnika_sl_logo_bw_pion_pl_rgb.png").toURI());

        document.open();

        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.setAbsolutePosition(220,0);
        document.add(img);

        int meetingsAmount = groupDto.getMeetings().size();
        addPdfInfo(writer, groupDto);

        PdfPTable table = new PdfPTable(meetingsAmount + 3);
        table.setWidthPercentage(100);
        addTableHeader(table, groupDto);
        addRows(table, groupDto);

        document.add(table);


        document.close();

        File file = new File("finalResults.pdf");
        FileInputStream input = new FileInputStream(file);

        fileStorageService.storeFile("finalResult.pdf", input, groupId, file.length());

        return groupDto;
    }

    private void addRows(PdfPTable table, GroupDto groupDto) {
        groupDto.getStudentGroupList().forEach(

                i ->
                {
                    table.addCell(i.getStudent().getFirstName());
                    table.addCell(i.getStudent().getLastName());
                    table.addCell(i.getMark().toString());
                    groupDto.getMeetings().stream()
                .flatMap(u -> u.getPresenceList().stream())
                            .filter(s -> s.getStudentGroup().getStudent().getAlbumNo().equals(i.getStudent().getAlbumNo()))
                            .collect(Collectors.toList())
                            .forEach(
                                    x -> table.addCell(x.getWasPresent().toString())

                    );
                }
        );
    }

    @SneakyThrows
    private void addPdfInfo(PdfWriter writer, GroupDto groupDto) {
        String lec = String.format("Lecturer: %s %s", groupDto.getTopic().getLecturer().getFirstName(), groupDto.getTopic().getLecturer().getLastName());
        String topic = String.format("Topic: %s ", groupDto.getTopic().getTopicName());


        FixText(lec,0,700,writer,14);
        FixText(topic,0,600,writer,14);
    }

    private void addTableHeader(PdfPTable table, GroupDto groupDto) {
        int meetingsAmount = groupDto.getMeetings().size();

        List<String> meetings = new ArrayList<>();

        for(int i = 0 ; i< meetingsAmount; ++i) {
            meetings.add(String.format("Meeting nr %d", i +1 ));
        }

        Stream.of("Firstname", "Lastname", "Mark")
                .forEach(columnTitle -> {
                    createHeader(table, columnTitle);
                });

        meetings.forEach(columnTitle -> {
            createHeader(table, columnTitle);
        });
    }

    private void createHeader(PdfPTable table, String columnTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(columnTitle));
        table.addCell(header);
    }

    private static void FixText(String text, int x, int y,PdfWriter writer,int size) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, size);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


}
