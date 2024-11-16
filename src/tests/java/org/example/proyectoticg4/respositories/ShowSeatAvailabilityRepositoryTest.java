package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ShowSeatAvailabilityRepositoryTest {

    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testSaveAndFindById() {
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
        Show savedShow = showRepository.save(show);

        // Crear y guardar un asiento
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
        ShowSeatAvailability savedAvailability = showSeatAvailabilityRepository.save(seatAvailability);

        // Recuperar la disponibilidad por su ID
        Optional<ShowSeatAvailability> foundAvailability = showSeatAvailabilityRepository.findById(availabilityId);

        // Verificaciones
        assertThat(foundAvailability).isPresent();
        assertThat(foundAvailability.get().getId()).isEqualTo(availabilityId);
        assertThat(foundAvailability.get().getAvailable()).isTrue();
    }

    @Test
    public void testFindAll() {
        // Configuración similar al test anterior, pero guardando varias disponibilidades

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
        Show savedShow = showRepository.save(show);

        // Crear y guardar asientos y sus disponibilidades
        for (int i = 1; i <= 3; i++) {
            SeatId seatId = new SeatId(savedCinema.getCiNumber(), hallId.gethNumber(), 'A', i);
            Seat seat = new Seat();
            seat.setSeatId(seatId);
            seat.setHall(hall);
            seatRepository.save(seat);

            ShowSeatAvailabilityId availabilityId = new ShowSeatAvailabilityId(savedShow.getShowCode(), seatId);
            ShowSeatAvailability seatAvailability = new ShowSeatAvailability();
            seatAvailability.setId(availabilityId);
            seatAvailability.setShow(savedShow);
            seatAvailability.setAvailable(true);
            showSeatAvailabilityRepository.save(seatAvailability);
        }

        // Recuperar todas las disponibilidades
        Iterable<ShowSeatAvailability> availabilities = showSeatAvailabilityRepository.findAll();

        // Verificaciones
        assertThat(availabilities).hasSize(3);
    }

    @Test
    public void testDelete() {
        // Crear y guardar todos los datos necesarios
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Sur");
        Cinema savedCinema = cinemaRepository.save(cinema);

        HallId hallId = new HallId(savedCinema.getCiNumber(), 3);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hallRepository.save(hall);

        Movie movie = new Movie();
        movie.setMovieId("M3");
        movie.setTitle("Dunkirk");
        movieRepository.save(movie);

        Show show = new Show();
        show.setMovie(movie);
        show.setHall(hall);
        show.setShowTime(LocalDateTime.now());
        show.setPrice(110);
        Show savedShow = showRepository.save(show);

        SeatId seatId = new SeatId(savedCinema.getCiNumber(), hallId.gethNumber(), 'B', 1);
        Seat seat = new Seat();
        seat.setSeatId(seatId);
        seat.setHall(hall);
        seatRepository.save(seat);

        ShowSeatAvailabilityId availabilityId = new ShowSeatAvailabilityId(savedShow.getShowCode(), seatId);
        ShowSeatAvailability seatAvailability = new ShowSeatAvailability();
        seatAvailability.setId(availabilityId);
        seatAvailability.setShow(savedShow);
        seatAvailability.setAvailable(true);
        showSeatAvailabilityRepository.save(seatAvailability);

        // Eliminar la disponibilidad
        showSeatAvailabilityRepository.deleteById(availabilityId);

        // Verificar que la disponibilidad ha sido eliminada
        Optional<ShowSeatAvailability> foundAvailability = showSeatAvailabilityRepository.findById(availabilityId);
        assertThat(foundAvailability).isNotPresent();
    }
}
