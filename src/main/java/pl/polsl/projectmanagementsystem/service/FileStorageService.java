package pl.polsl.projectmanagementsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.polsl.projectmanagementsystem.config.FileStorageProperties;
import pl.polsl.projectmanagementsystem.dto.FileResponseDto;
import pl.polsl.projectmanagementsystem.exception.FileStorageException;
import pl.polsl.projectmanagementsystem.exception.GroupNotFoundException;
import pl.polsl.projectmanagementsystem.model.Group;
import pl.polsl.projectmanagementsystem.repository.GroupRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;
    private final GroupRepository groupRepository;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, GroupRepository groupRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        this.groupRepository = groupRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Transactional
    public FileResponseDto storeFile(String fileNameEntry, InputStream stream, Long groupId, long size) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(fileNameEntry);

        Group groupById = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group with given id not found"));

        String finalPath = String.format("%d/%s", groupById.getId(), fileName);

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(finalPath);
            Files.createDirectories(Paths.get(String.valueOf(this.fileStorageLocation.resolve(groupById.getId().toString()))));
            Files.copy(stream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            groupById.getFilePaths().add(fileName);

            return new FileResponseDto(fileName, size);
        } catch (IOException ex) {
            log.warn("----------------------");
            ex.printStackTrace();
            log.warn("----------------------");

            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, Long groupId) {
        Group groupById = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group with given id not found"));

        String finalPath = String.format("%d/%s", groupById.getId(), fileName);

        try {
            Path filePath = this.fileStorageLocation.resolve(finalPath).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("File not found " + fileName, ex);
        }
    }
}
