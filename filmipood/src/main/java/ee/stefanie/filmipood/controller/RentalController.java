package ee.stefanie.filmipood.controller;

import ee.stefanie.filmipood.entity.Film;
import ee.stefanie.filmipood.entity.Rental;
import ee.stefanie.filmipood.model.FilmReturn;
import ee.stefanie.filmipood.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentalController {

    @Autowired
    RentalService rentalService;

    @PostMapping("start-rental")
    public Rental startRental(@RequestBody List<Film> films) throws Exception {
        return rentalService.startRental(films);
    }

    @PostMapping("end-rental")
    public List<Rental> endRental(@RequestBody List<FilmReturn> films) {
        return rentalService.endRental(films);
    }

    @GetMapping("bonus-points")
    public int getBonusPoints() {
        return rentalService.getBonusPoints();
    }
}
