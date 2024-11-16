package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ShowRepositoryTest {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @Test
    public void testFindShowsByCinemaNumber() {
        // Crear y guardar un cine
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear y guardar una sala
        HallId hallId = new HallId(savedCinema.getCiNumber(), 1);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hallRepository.save(hall);

        // Crear y guardar una película
        Movie movie = new Movie();
        movie.setMovieId("M1");
        movie.setTitle("Inception");
        movieRepository.save(movie);

        // Crear y guardar un show
        Show show = new Show();
        show.setMovie(movie);
        show.setHall(hall);
        show.setShowTime(LocalDateTime.now());
        show.setPrice(100);
        showRepository.save(show);

        // Llamar al método a probar
        List<Show> shows = showRepository.findShowsByCinemaNumber(savedCinema.getCiNumber());

        // Verificaciones
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0).getHall().getCinema().getCiNumber()).isEqualTo(savedCinema.getCiNumber());
    }

    @Test
    public void testFindByMovieIdAndCinemaNumber() {
        // Crear y guardar un cine
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Norte");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear y guardar una sala
        HallId hallId = new HallId(savedCinema.getCiNumber(), 2);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hallRepository.save(hall);

        // Crear y guardar una película
        Movie movie = new Movie();
        movie.setMovieId("M2");
        movie.setTitle("Interstellar");
        movieRepository.save(movie);

        // Crear y guardar un show
        Show show = new Show();
        show.setMovie(movie);
        show.setHall(hall);
        show.setShowTime(LocalDateTime.now());
        show.setPrice(120);
        showRepository.save(show);

        // Llamar al método a probar
        List<Show> shows = showRepository.findByMovieIdAndCinemaNumber("M2", savedCinema.getCiNumber());

        // Verificaciones
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0).getMovie().getMovieId()).isEqualTo("M2");
        assertThat(shows.get(0).getHall().getCinema().getCiNumber()).isEqualTo(savedCinema.getCiNumber());
    }

    @Test
    public void testFindSeats() {
        // Crear y guardar un cine
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Sur");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear y guardar una sala
        HallId hallId = new HallId(savedCinema.getCiNumber(), 3);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hallRepository.save(hall);

        // Crear y guardar una película
        Movie movie = new Movie();
        movie.setMovieId("M3");
        movie.setTitle("Dunkirk");
        movieRepository.save(movie);

        // Crear y guardar un show
        LocalDateTime showTime = LocalDateTime.now();
        Show show = new Show();
        show.setMovie(movie);
        show.setHall(hall);
        show.setShowTime(showTime);
        show.setPrice(110);
        Show savedShow = showRepository.save(show);

        // Crear y guardar asientos
        SeatId seatId = new SeatId(savedCinema.getCiNumber(), hallId.gethNumber(), 'A', 1);
        Seat seat = new Seat();
        seat.setSeatId(seatId);
        seat.setHall(hall);
        seatRepository.save(seat);

        // Crear y guardar la disponibilidad del asiento para el show
        ShowSeatAvailabilityId availabilityId = new ShowSeatAvailabilityId(savedShow.getShowCode(), seatId);
        ShowSeatAvailability seatAvailability = new ShowSeatAvailability();
        seatAvailability.setId(availabilityId);
        seatAvailability.setShow(savedShow);
        seatAvailability.setAvailable(true);
        showSeatAvailabilityRepository.save(seatAvailability);

        // Llamar al método a probar
        List<ShowSeatAvailability> seatAvailabilities = showRepository.findSeats("M3", savedCinema.getCiNumber(), showTime);

        // Verificaciones
        assertThat(seatAvailabilities).isNotEmpty();
        assertThat(seatAvailabilities.get(0).getShow().getMovie().getMovieId()).isEqualTo("M3");
        assertThat(seatAvailabilities.get(0).getShow().getHall().getCinema().getCiNumber()).isEqualTo(savedCinema.getCiNumber());
        assertThat(seatAvailabilities.get(0).getShow().getShowTime()).isEqualTo(showTime);
    }
}
