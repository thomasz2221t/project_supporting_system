package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.StudentGroup;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    @Modifying
    @Query(value = "delete from StudentGroup sg where sg.id = ?1")
    void deleteStudentGroup(Long id);
}