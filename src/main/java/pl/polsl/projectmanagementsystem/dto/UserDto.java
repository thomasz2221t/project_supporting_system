package pl.polsl.projectmanagementsystem.dto;


import lombok.*;
import pl.polsl.projectmanagementsystem.dto.enums.RoleDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}