package ee.stefanie.kumnevoistlus.repository;

import ee.stefanie.kumnevoistlus.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
