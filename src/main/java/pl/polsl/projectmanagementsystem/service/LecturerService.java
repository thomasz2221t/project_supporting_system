package pl.polsl.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.dto.LecturerDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.LecturerMapper;
import pl.polsl.projectmanagementsystem.repository.LecturerRepository;

@Service
@RequiredArgsConstructor
public class LecturerService {

    private final KeycloakService keycloakService;
    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;

    public UserDto createNewLecturer(UserDto userDto, LecturerDto lecturerDto) {
        userDto.setRole("lecturer");

        String userId = keycloakService.addUser(userDto);

        lecturerDto.setUserId(userId);

        lecturerRepository.save(lecturerMapper.mapDtoToEntity(lecturerDto));

        return userDto;
    }
}
