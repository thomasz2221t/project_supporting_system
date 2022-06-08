package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Semester;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Optional<Semester> findByFieldOfStudyAndYearAndSemester(String fieldOfStudy, Integer year, Integer semester);

    @Query(value = "select s from Semester s join s.studentSemesterList sl join sl.student st where st.albumNo = ?1")
    List<Semester> findAllStudentSemesters(String studentId);
}
