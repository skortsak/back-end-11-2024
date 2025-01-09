package ee.stefanie.filmipood.controller;

import ee.stefanie.filmipood.entity.Film;
import ee.stefanie.filmipood.entity.FilmType;
import ee.stefanie.filmipood.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {

    @Autowired
    FilmRepository filmRepository;

    @PostMapping("films") // front-end peaks saatma NAME ja TYPE
    public List<Film> addFilm(@RequestBody Film film) {
        film.setDays(0);
        film.setAvailable(true);
        film.setRental(null);
        filmRepository.save(film);
        return filmRepository.findAll();
    }

    @DeleteMapping("films/{id}")
    public List<Film> removeFilm(@PathVariable Long id) {
        filmRepository.deleteById(id);
        return filmRepository.findAll();
    }

    @PatchMapping("films")
    public List<Film> changeFilmType(@RequestParam Long filmId, FilmType filmType) {
        Film film = filmRepository.findById(filmId).orElseThrow();
        film.setType(filmType);
        filmRepository.save(film);
        return filmRepository.findAll();
    }

    @GetMapping("films")
    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("available-films")
    public List<Film> getAvailableFilms() {
        return filmRepository.findByAvailableTrue();
    }
}
