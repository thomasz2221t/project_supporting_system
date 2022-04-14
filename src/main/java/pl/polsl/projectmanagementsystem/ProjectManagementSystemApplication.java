package pl.polsl.projectmanagementsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ProjectManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);
    }
}
