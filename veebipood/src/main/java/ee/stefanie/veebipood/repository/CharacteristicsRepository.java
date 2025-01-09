package ee.stefanie.veebipood.repository;

import ee.stefanie.veebipood.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicsRepository extends JpaRepository<Characteristic, Long> {
}
