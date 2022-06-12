package pl.polsl.projectmanagementsystem.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.projectmanagementsystem.dto.PresenceDto;
import pl.polsl.projectmanagementsystem.model.Presence;

import java.util.List;

@Registered
public interface PresenceRepository extends JpaRepository<Presence, Long> {

    List<Presence> findAllByIdIn(List<Long> idList);
}
