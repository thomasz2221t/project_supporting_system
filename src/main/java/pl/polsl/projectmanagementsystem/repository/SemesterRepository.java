package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Semester;

import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Optional<Semester> findByFieldOfStudyAndYearAndSemester(String fieldOfStudy, Integer year, Integer semester);
}
