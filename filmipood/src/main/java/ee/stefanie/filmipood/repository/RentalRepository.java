package ee.stefanie.filmipood.repository;

import ee.stefanie.filmipood.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
