package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.*;
import pl.polsl.projectmanagementsystem.exception.UserNotFoundException;
import pl.polsl.projectmanagementsystem.mapper.lecturer.LecturerMapper;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.model.Lecturer;
import pl.polsl.projectmanagementsystem.model.Student;
import pl.polsl.projectmanagementsystem.model.Topic;
import pl.polsl.projectmanagementsystem.repository.LecturerRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerService {

    private final KeycloakService keycloakService;
    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;
    private final UserMapper userMapper;

    @Transactional
    public LecturerDto createNewLecturer(UserDto userDto, LecturerDto lecturerDto) {
        userDto.setRole("LECTURER");

        String userId = keycloakService.addUser(userDto);

        lecturerDto.setUserId(userId);

        Lecturer save = lecturerRepository.save(lecturerMapper.mapDtoToNewEntity(lecturerDto));

        lecturerDto = lecturerMapper.mapEntityToDto(save);

        return lecturerDto;
    }

    @Transactional
    public UserDto deleteLecturer(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Lecturer not found"));

        UserRepresentation userRepresentation = keycloakService.deleteUser(lecturer.getUserId());

        lecturer.setIsActive(false);

        return userMapper.mapModelApiToDto(userRepresentation);
    }

    @Transactional
    public LecturerDto updateLecturer(UserDto userDto, LecturerDto lecturerDto, Long id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Lecturer not found"));

        userDto.setRole("LECTURER");

        keycloakService.updateUser(userDto, lecturer.getUserId());

        lecturerDto.setUserId(lecturer.getUserId());

        lecturer = lecturerMapper.mapDtoToEntity(lecturerDto);
        lecturer.setId(id);

        lecturerRepository.save(lecturer);

        return lecturerMapper.mapEntityToDto(lecturer);
    }

    public LecturerDto getInfo() {
        String userId = keycloakService.getUserId();

        Lecturer student = lecturerRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("Lecturer not found"));

        return lecturerMapper.mapEntityToDto(student);
    }

    public FindResultDto<LecturerDto> getAllLecturers(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage().intValue(), searchDto.getLimit().intValue());

        Page<Lecturer> topicList = lecturerRepository.findAll(pageRequest);

        return FindResultDto.<LecturerDto>builder()
                .count((long) topicList.getNumberOfElements())
                .results(topicList.getContent().stream()
                        .map(lecturerMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .startElement(pageRequest.getOffset())
                .totalCount(topicList.getTotalElements())
                .build();
    }

    public LecturerDto getLecturerById(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Lecturer not found"));

        UserRepresentation serviceUser = keycloakService.getUser(lecturer.getUserId());

        LecturerDto lecturerDto = lecturerMapper.mapEntityToDto(lecturer);

        lecturerDto.setEmail(serviceUser.getEmail());

        return lecturerDto;
    }
}
