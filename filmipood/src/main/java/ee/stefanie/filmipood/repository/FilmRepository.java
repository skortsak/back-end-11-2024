package ee.stefanie.filmipood.repository;

import ee.stefanie.filmipood.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {


    List<Film> findByAvailableTrue();
}
