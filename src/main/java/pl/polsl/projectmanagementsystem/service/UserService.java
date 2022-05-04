package pl.polsl.projectmanagementsystem.service;


import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.mapper.user.UserMapper;
import pl.polsl.projectmanagementsystem.repository.LecturerRepository;
import pl.polsl.projectmanagementsystem.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakService keycloakService;
    private final UserMapper userMapper;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public UserDto deleteUser(String userId) {

        UserRepresentation userRepresentation = keycloakService.deleteUser(userId);

        UserDto userDto = userMapper.mapModelApiToDto(userRepresentation);

        if(userDto.getRole().equals("STUDENT")) {
            studentRepository.deleteByUserId(userId);
        } else if(userDto.getRole().equals("LECTURER")) {
            lecturerRepository.deleteByUserId(userId);
        }

        return userDto;
    }
}
