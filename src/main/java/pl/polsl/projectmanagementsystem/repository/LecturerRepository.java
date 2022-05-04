package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Lecturer;
import pl.polsl.projectmanagementsystem.model.Student;

import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    Optional<Lecturer> findByUserId(String userId);
    void deleteByUserId(String userId);
}
