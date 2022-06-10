package pl.polsl.projectmanagementsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.polsl.projectmanagementsystem.config.FileStorageProperties;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ProjectManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);
    }
}
