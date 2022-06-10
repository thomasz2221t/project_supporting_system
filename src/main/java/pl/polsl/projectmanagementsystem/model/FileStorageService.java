package pl.polsl.projectmanagementsystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.management.api.model.FileResponseModelApi;
import pl.polsl.projectmanagementsystem.config.FileStorageProperties;
import pl.polsl.projectmanagementsystem.dto.FileResponseDto;
import pl.polsl.projectmanagementsystem.exception.FileStorageException;
import pl.polsl.projectmanagementsystem.service.GroupService;
import pl.polsl.projectmanagementsystem.service.StudentService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final GroupService groupService;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, GroupService groupService) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        this.groupService = groupService;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Transactional
    public FileResponseDto storeFile(MultipartFile file, Long groupId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Group groupById = groupService.findGroupById(groupId);

        String finalPath = String.format("%d/%s", groupById.getId(), fileName);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(finalPath);
            Files.createDirectories(Paths.get(String.valueOf(this.fileStorageLocation.resolve(groupById.getId().toString()))));
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            groupById.getFilePaths().add(fileName);

            return new FileResponseDto(fileName,
                    file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, Long groupId) {
        Group groupById = groupService.findGroupById(groupId);

        String finalPath = String.format("%d/%s", groupById.getId(), fileName);

        try {
            Path filePath = this.fileStorageLocation.resolve(finalPath).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("File not found " + fileName, ex);
        }
    }
}
