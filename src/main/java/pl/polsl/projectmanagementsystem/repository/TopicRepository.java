package pl.polsl.projectmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.projectmanagementsystem.model.Topic;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAllByIsActiveTrue(Pageable pageable);
    Optional<Topic> findByIdAndIsActiveTrue(Long id);
    List<Topic> findAllByTopicName(String topicName);
}
