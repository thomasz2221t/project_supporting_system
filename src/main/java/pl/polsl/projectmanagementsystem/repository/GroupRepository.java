package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Group;
import pl.polsl.projectmanagementsystem.model.StudentGroup;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select g from Group g join g.semester s join g.studentGroupList gl join gl.student where s.id = ?1")
    @EntityGraph(attributePaths = {"semester", "studentGroupList", "studentGroupList.student"})
    Page<Group> findGroupsBySemester(Long semesterId, PageRequest pageRequest);

    @Query(value = "select gg from Group g join g.studentGroupList gg where g.id = ?1")
    List<StudentGroup> findStudentGroupList(Long groupId);
}
