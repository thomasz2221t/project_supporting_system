package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Lecturer;
import pl.polsl.projectmanagementsystem.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findAllByAlbumNoIn(List<String> stringList);
    Optional<Student> findByUserId(String userId);
    Optional<Student> findByAlbumNo(String albumNo);
    Page<Student> findAllByIsActiveTrue(Pageable pageable);


    void deleteByUserId(String userId);

    @Query(value = "select s from Student s join s.studentSemesterList sl join sl.semester sm where sm.id = ?1")
    Page<Student> findStudentsBySemester(Long semesterId, PageRequest pageRequest);

    @Query(value = "select s from Student s join s.studentSemesterList sl join sl.semester sm where s.albumNo in ?1 and sm.id = ?2")
    List<Student> findStudentsBySemester(List<String> albumNumbers, Long semesterId);
}
