package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.SemesterDto;
import pl.polsl.projectmanagementsystem.mapper.SemesterMapper;
import pl.polsl.projectmanagementsystem.model.Semester;
import pl.polsl.projectmanagementsystem.repository.SemesterRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    public List<SemesterDto> getAllSemesters() {
        return semesterRepository.findAll().stream()
                .map(semesterMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
