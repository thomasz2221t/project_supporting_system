package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.LecturerApi;
import pl.polsl.management.api.model.*;
import pl.polsl.projectmanagementsystem.dto.*;
import pl.polsl.projectmanagementsystem.mapper.lecturer.LecturerFindResponseMapper;
import pl.polsl.projectmanagementsystem.mapper.lecturer.LecturerMapper;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.service.LecturerService;


@RestController
@RequiredArgsConstructor
public class LecturerController implements LecturerApi {

    private final LecturerService lecturerService;
    private final UserMapper userMapper;
    private final LecturerMapper lecturerMapper;
    private final LecturerFindResponseMapper lecturerFindResponseMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<LecturerResponseModelApi> createLecturer(LecturerModelApi userModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userModelApi);
        LecturerDto lecturerDto = lecturerMapper.mapModelApiToDto(userModelApi);

        LecturerDto response = lecturerService.createNewLecturer(userDto, lecturerDto);

        return new ResponseEntity<>(lecturerMapper.mapDtoToModelApi(response), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<UserResponseModelApi> deleteLecturer(Long id) {
        UserDto userDto = lecturerService.deleteLecturer(id);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<LecturerResponseModelApi> updateLecturer(Long id, LecturerUpdateModelApi lecturerModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(lecturerModelApi);
        LecturerDto lecturerDto = lecturerMapper.mapModelApiToDto(lecturerModelApi);

        LecturerDto response = lecturerService.updateLecturer(userDto, lecturerDto, id);

        return new ResponseEntity<>(lecturerMapper.mapDtoToModelApi(response), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<LecturerFindResponseModelApi> getAllLecturers(Long page, Long limit) {
        SearchDto searchDto = SearchDto.builder()
                .page(page)
                .limit(limit)
                .build();

        FindResultDto<LecturerDto> findResult = lecturerService.getAllLecturers(searchDto);

        return new ResponseEntity<>(lecturerFindResponseMapper.mapDtoToModelApi(findResult), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<LecturerResponseModelApi> getLecturerInfo() {
        LecturerDto lecturerDto = lecturerService.getInfo();

        return new ResponseEntity<>(lecturerMapper.mapDtoToModelApi(lecturerDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<LecturerResponseModelApi> getLecturerById(Long id) {
        LecturerDto lecturerDto = lecturerService.getLecturerById(id);

        return new ResponseEntity<>(lecturerMapper.mapDtoToModelApi(lecturerDto), HttpStatus.OK);
    }
}
