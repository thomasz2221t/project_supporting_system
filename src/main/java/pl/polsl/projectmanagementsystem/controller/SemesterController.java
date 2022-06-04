package pl.polsl.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.SemesterApi;
import pl.polsl.management.api.model.SemesterResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.SemesterDto;
import pl.polsl.projectmanagementsystem.mapper.SemesterMapper;
import pl.polsl.projectmanagementsystem.service.SemesterService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SemesterController implements SemesterApi {

    private final SemesterService semesterService;
    private final SemesterMapper semesterMapper;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_lecturer', 'ROLE_admin', 'ROLE_student')")
    @CrossOrigin
    public ResponseEntity<List<SemesterResponseModelApi>> getAllSemesters() {
        List<SemesterDto> semesters = semesterService.getAllSemesters();

        return new ResponseEntity<>(semesters.stream().map(semesterMapper::mapDtoToModelApi).collect(Collectors.toList()), HttpStatus.OK);
    }
}