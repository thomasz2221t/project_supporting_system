package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
