package ee.stefanie.filmipood;

import ee.stefanie.filmipood.entity.Film;
import ee.stefanie.filmipood.entity.FilmType;
import ee.stefanie.filmipood.entity.Rental;
import ee.stefanie.filmipood.model.FilmReturn;
import ee.stefanie.filmipood.repository.FilmRepository;
import ee.stefanie.filmipood.repository.RentalRepository;
import ee.stefanie.filmipood.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RentalServiceTests {

    @Mock
    RentalRepository rentalRepository;

    @Mock
    FilmRepository filmRepository;

    @InjectMocks
    RentalService rentalService;

    List<Film> films = new ArrayList<>();
    Film film1 = new Film();
    Film film2 = new Film();
    Rental rental = new Rental();
    List<FilmReturn> filmReturns = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        film1.setId(1L);
        film1.setAvailable(true);
        film2.setId(2L);
        film2.setAvailable(true);
        films.add(film1);
        films.add(film2);
        rental.setId(1L);
        when(rentalRepository.save(any())).thenReturn(rental);
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film1));
        when(filmRepository.findById(2L)).thenReturn(Optional.of(film2));

        FilmReturn filmReturn = new FilmReturn();
        filmReturn.setId(1L);
        filmReturn.setTotalDays(7);
        filmReturns.add(filmReturn);
    }

    // given_when_then()
    @Test
    void givenFilmIsEmpty_whenRentalIsStarted_thenExceptionIsThrown(){
        Film film3 = new Film();
        film2.setId(3L);
        films.add(film3);
        assertThrows(NoSuchElementException.class, () -> rentalService.startRental(films));
    }

    @Test
    void givenFilmIsNotAvailable_whenRentalIsStarted_thenExceptionIsThrown() throws RuntimeException {
        film1.setAvailable(false);
        assertThrows(RuntimeException.class, () -> rentalService.startRental(films));
    }


    @Test
    void whenRentalIsStarted_thenLateFeeIs0() throws Exception {
        rental.setLateFee(99);
        rentalService.startRental(films);
        assertEquals(0, rental.getLateFee());
    }

    @Test
    void givenOldFilmIsRented5Days_whenRentalIsStarted_thenSumIs3() throws Exception {
        film1.setDays(5);
        film1.setType(FilmType.OLD);
        rentalService.startRental(films);
        assertEquals(3, rental.getSum());
    }

    @Test
    void givenOldFilmIsRented7Days_whenRentalIsStarted_thenSumIs9() throws Exception {
        film1.setDays(7);
        film1.setType(FilmType.OLD);
        rentalService.startRental(films);
        assertEquals(9, rental.getSum());
    }

    @Test
    void givenNewFilmIsRented7Days_whenRentalIsStarted_thenSumIs28() throws Exception {
        film1.setDays(7);
        film1.setType(FilmType.NEW);
        rentalService.startRental(films);
        assertEquals(28, rental.getSum());
    }

    @Test
    void givenRegularFilmIsDelayed2Days_whenRentalIsEnded_thenLateFeeIs6() throws Exception {
        film1.setDays(5);
        film1.setType(FilmType.REGULAR);
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film1));
        rentalService.startRental(films);
        rentalService.endRental(filmReturns);
        assertEquals(6, rental.getLateFee());
    }

    @Test
    void givenRegularFilmIsNotDelayed_whenRentalIsEnded_thenLateFeeIs0() throws Exception {
        film1.setDays(7);
        film1.setType(FilmType.REGULAR);
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film1));
        rentalService.startRental(films);
        rentalService.endRental(filmReturns);
        assertEquals(0, rental.getLateFee());
    }

    @Test
    void whenRegularFilmIsRented_thenBonusPointsAre1() throws Exception {
        film1.setDays(2);
        film1.setType(FilmType.REGULAR);
        rentalService.startRental(films);
        rentalService.endRental(filmReturns);
        assertEquals(1, rentalService.getBonusPoints());
    }

    @Test
    void whenNewFilmIsRented_thenBonusPointsAre2() throws Exception {
        film1.setDays(2);
        film1.setType(FilmType.NEW);
        rentalService.startRental(films);
        rentalService.endRental(filmReturns);
        assertEquals(2, rentalService.getBonusPoints());
    }
}
