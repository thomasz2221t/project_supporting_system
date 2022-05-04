package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

//    @Query(value = "select gs from Semester  s join fetch s.groups gs join fetch gs.topic join fetch s.studentSemesterList sm join fetch sm.student where s.id = ?1")
//    Page<Group> findGroupsBySemester(Long semesterId, PageRequest pageRequest);
}
