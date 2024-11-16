package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Reservation;
import org.example.proyectoticg4.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        // Crear una instancia de User
        User user = new User();
        user.setUserId("user1");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        // Crear una instancia de Reservation
        Reservation reservation = new Reservation();
        reservation.setUser(savedUser);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setTotal(100.0);

        // Guardar la reserva en la base de datos
        Reservation savedReservation = reservationRepository.save(reservation);

        // Recuperar la reserva por su ID
        Reservation foundReservation = reservationRepository.findById(savedReservation.getId()).orElse(null);

        // Verificaciones
        assertThat(foundReservation).isNotNull();
        assertThat(foundReservation.getUser().getUserId()).isEqualTo("user1");
        assertThat(foundReservation.getTotal()).isEqualTo(100.0);
    }

    @Test
    public void testFindByUser_UserId() {
        // Crear una instancia de User
        User user = new User();
        user.setUserId("user1");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        // Crear y guardar varias reservas para el usuario
        Reservation reservation1 = new Reservation();
        reservation1.setUser(savedUser);
        reservation1.setReservationTime(LocalDateTime.now());
        reservation1.setTotal(50.0);

        Reservation reservation2 = new Reservation();
        reservation2.setUser(savedUser);
        reservation2.setReservationTime(LocalDateTime.now());
        reservation2.setTotal(75.0);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        // Crear y guardar una reserva para otro usuario
        User otherUser = new User();
        otherUser.setUserId("user2");
        otherUser.setPassword("password");
        userRepository.save(otherUser);

        Reservation otherReservation = new Reservation();
        otherReservation.setUser(otherUser);
        otherReservation.setReservationTime(LocalDateTime.now());
        otherReservation.setTotal(100.0);

        reservationRepository.save(otherReservation);

        // Recuperar las reservas del usuario "user1"
        List<Reservation> reservations = reservationRepository.findByUser_UserId("user1");

        // Verificaciones
        assertThat(reservations).hasSize(2);
        for (Reservation res : reservations) {
            assertThat(res.getUser().getUserId()).isEqualTo("user1");
        }
    }

    @Test
    public void testDelete() {
        // Crear una instancia de User
        User user = new User();
        user.setUserId("user1");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        // Crear y guardar una reserva
        Reservation reservation = new Reservation();
        reservation.setUser(savedUser);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setTotal(100.0);

        Reservation savedReservation = reservationRepository.save(reservation);

        // Eliminar la reserva
        reservationRepository.deleteById(savedReservation.getId());

        // Verificar que la reserva ha sido eliminada
        boolean exists = reservationRepository.existsById(savedReservation.getId());
        assertThat(exists).isFalse();
    }
}
