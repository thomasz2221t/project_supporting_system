package pl.polsl.projectmanagementsystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.polsl.projectmanagementsystem.config.FileStorageProperties;
import pl.polsl.projectmanagementsystem.config.KeycloakConfig;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@Slf4j
public class ProjectManagementSystemApplication implements CommandLineRunner {

    @Value("${management.secret-key}")
    private String name;

    private static String NAME_STATIC;

    @Value("${management.secret-key}")
    public void setNameStatic(String name){
        ProjectManagementSystemApplication.NAME_STATIC = name;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.warn("----------------------------");
        log.warn("client secret: {}", NAME_STATIC);
        log.warn("----------------------------");
    }
}
