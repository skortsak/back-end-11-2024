package ee.stefanie.filmipood.service;

import ee.stefanie.filmipood.entity.Film;
import ee.stefanie.filmipood.entity.Rental;
import ee.stefanie.filmipood.model.FilmReturn;
import ee.stefanie.filmipood.repository.FilmRepository;
import ee.stefanie.filmipood.repository.RentalRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// KODUS: Nortali töö
// KODUS: Siia Rentalisse front-end

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    FilmRepository filmRepository;

    // kui on siin final, siis Java mõistes on tegemist const ehk väärtust ei saa enam rphkem anda
    private final int PREMIUM_PRICE = 4;
    private final int BASIC_PRICE = 3;

    @Getter
    private int bonusPoints = 0;

    //@Transactional // keerab kõik andmebaasitegemised tagasi siit funktsioonist, kui tuli kuskil kohas exception
    public Rental startRental(List<Film> films) throws Exception {
        for (Film film : films) {
            Film dbFilm = filmRepository.findById(film.getId()).orElseThrow();
            if (!dbFilm.isAvailable()) {
                throw new RuntimeException("Film with id " + dbFilm.getId() + " not available!");
            }
        }

        Rental rental = new Rental();
        rental = rentalRepository.save(rental); // saving to get ID
        int sum = 0;
        for (Film frontendFilm : films) {
            Film dbFilm = filmRepository.findById(frontendFilm.getId()).orElseThrow();
            dbFilm.setDays(frontendFilm.getDays());
            dbFilm.setAvailable(false);
            dbFilm.setRental(rental);
            filmRepository.save(dbFilm);

            sum += calculateFilmPrice(dbFilm);
        }
        rental.setSum(sum);
        rental.setLateFee(0);
        return rentalRepository.save(rental);
    }

    private int calculateFilmPrice(Film dbFilm) {
        switch (dbFilm.getType()) {
            case NEW -> {
                // 10 päeva: 10 * 4 = 40
                return dbFilm.getDays() * PREMIUM_PRICE;
            }
            case REGULAR -> {
                // 10 päeva: 3 + 3 * 7 = 3 + 21 = 24
                if (dbFilm.getDays() > 3) {
                    return BASIC_PRICE + BASIC_PRICE * (dbFilm.getDays() - 3);
                }
                return BASIC_PRICE;
            }
            case OLD -> {
                // 10 päeva: 3 + 3 * 5 = 3 + 15 = 18
                if (dbFilm.getDays() > 5) {
                    return BASIC_PRICE + BASIC_PRICE * (dbFilm.getDays() - 5);
                }
                return BASIC_PRICE;
            }
            case null, default -> {
                return 0;
            }
        }
    }

    public List<Rental> endRental(List<FilmReturn> films) {
        for (FilmReturn f: films) {
            Film film = filmRepository.findById(f.getId()).orElseThrow();
            film.setAvailable(true);
            Rental rental = film.getRental();
            if (f.getTotalDays() > film.getDays()) {
                rental.setLateFee(rental.getLateFee() + calculateLateFeeAndBonusPoints(film, f.getTotalDays()));
            }
            rentalRepository.save(rental);
            film.setDays(0); // enne kindlasti lateFee arvutamine
            film.setAvailable(true);
            film.setRental(null);
            filmRepository.save(film);
        }
        return rentalRepository.findAll();
    }

    private int calculateLateFeeAndBonusPoints(Film film, int totalDays) {
        switch (film.getType()) {
            case NEW -> {
                bonusPoints += 2;
                return (totalDays - film.getDays()) * PREMIUM_PRICE;
            }
            case REGULAR, OLD -> {
                bonusPoints += 1;
                return (totalDays - film.getDays()) * BASIC_PRICE;
            }
            case null, default -> {
                return 0;
            }
        }
    }
}
