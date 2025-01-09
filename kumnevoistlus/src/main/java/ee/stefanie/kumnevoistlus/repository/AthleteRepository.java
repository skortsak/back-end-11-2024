package ee.stefanie.kumnevoistlus.repository;

import ee.stefanie.kumnevoistlus.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
