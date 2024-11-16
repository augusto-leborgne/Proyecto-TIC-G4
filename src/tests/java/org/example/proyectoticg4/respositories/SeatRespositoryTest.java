package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Cinema;
import org.example.proyectoticg4.entities.Hall;
import org.example.proyectoticg4.entities.HallId;
import org.example.proyectoticg4.entities.Seat;
import org.example.proyectoticg4.entities.SeatId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallRepository hallRepository;

    @Test
    public void testSaveAndFindById() {
        // Crear una instancia de Cinema
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear una instancia de Hall
        HallId hallId = new HallId(savedCinema.getCiNumber(), 1);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hall.setSeats(100);
        Hall savedHall = hallRepository.save(hall);

        // Crear una instancia de Seat
        SeatId seatId = new SeatId(savedHall.getId().getCiNumber(), savedHall.getId().gethNumber(), 'A', 1);
        Seat seat = new Seat();
        seat.setSeatId(seatId);
        seat.setHall(savedHall);

        // Guardar el asiento en la base de datos
        Seat savedSeat = seatRepository.save(seat);

        // Recuperar el asiento por su ID
        Seat foundSeat = seatRepository.findById(seatId).orElse(null);

        // Verificaciones
        assertThat(foundSeat).isNotNull();
        assertThat(foundSeat.getSeatId()).isEqualTo(seatId);
        assertThat(foundSeat.getHall().getId()).isEqualTo(hallId);
    }

    @Test
    public void testFindAll() {
        // Crear una instancia de Cinema
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear una instancia de Hall
        HallId hallId = new HallId(savedCinema.getCiNumber(), 1);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hall.setSeats(100);
        Hall savedHall = hallRepository.save(hall);

        // Crear y guardar varios asientos
        SeatId seatId1 = new SeatId(savedHall.getId().getCiNumber(), savedHall.getId().gethNumber(), 'A', 1);
        Seat seat1 = new Seat();
        seat1.setSeatId(seatId1);
        seat1.setHall(savedHall);

        SeatId seatId2 = new SeatId(savedHall.getId().getCiNumber(), savedHall.getId().gethNumber(), 'A', 2);
        Seat seat2 = new Seat();
        seat2.setSeatId(seatId2);
        seat2.setHall(savedHall);

        seatRepository.save(seat1);
        seatRepository.save(seat2);

        // Recuperar todos los asientos
        List<Seat> seats = seatRepository.findAll();

        // Verificaciones
        assertThat(seats).hasSize(2);
    }

    @Test
    public void testDelete() {
        // Crear una instancia de Cinema
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear una instancia de Hall
        HallId hallId = new HallId(savedCinema.getCiNumber(), 1);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hall.setSeats(100);
        Hall savedHall = hallRepository.save(hall);

        // Crear y guardar un asiento
        SeatId seatId = new SeatId(savedHall.getId().getCiNumber(), savedHall.getId().gethNumber(), 'A', 1);
        Seat seat = new Seat();
        seat.setSeatId(seatId);
        seat.setHall(savedHall);

        Seat savedSeat = seatRepository.save(seat);

        // Eliminar el asiento
        seatRepository.delete(savedSeat);

        // Verificar que el asiento ha sido eliminado
        boolean exists = seatRepository.existsById(seatId);
        assertThat(exists).isFalse();
    }
}
