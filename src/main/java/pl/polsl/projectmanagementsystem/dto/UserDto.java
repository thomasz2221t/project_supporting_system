package pl.polsl.projectmanagementsystem.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}