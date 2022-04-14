package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    Lecturer findByUserId(String userId);
}
