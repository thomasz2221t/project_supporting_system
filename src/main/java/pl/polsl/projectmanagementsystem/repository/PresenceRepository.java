package pl.polsl.projectmanagementsystem.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.projectmanagementsystem.dto.PresenceDto;
import pl.polsl.projectmanagementsystem.model.Presence;

@Registered
public interface PresenceRepository extends JpaRepository<Presence, Long> {
}
