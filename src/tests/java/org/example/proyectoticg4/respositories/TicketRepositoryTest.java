package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        // Create and save a User
        User user = new User();
        user.setUserId("user1");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        // Create and save a Reservation
        Reservation reservation = new Reservation();
        reservation.setUser(savedUser);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setTotal(100.0);
        Reservation savedReservation = reservationRepository.save(reservation);

        // Create and save a Ticket
        Ticket ticket = new Ticket();
        ticket.setReservation(savedReservation);
        ticket.setSeatColumn('A');
        ticket.setSeatRow(1);
        ticket.setHallNumber(1);
        ticket.setCinemaNumber(1);
        ticket.setShowTime(LocalDateTime.now());
        ticket.setMovieName("Movie1");

        Ticket savedTicket = ticketRepository.save(ticket);

        // Retrieve the Ticket by its ID
        Optional<Ticket> foundTicket = ticketRepository.findById(savedTicket.gettCode());

        // Assertions
        assertThat(foundTicket).isPresent();
        assertThat(foundTicket.get().gettCode()).isEqualTo(savedTicket.gettCode());
        assertThat(foundTicket.get().getReservation().getId()).isEqualTo(savedReservation.getId());
        assertThat(foundTicket.get().getSeatRow()).isEqualTo(1);
        assertThat(foundTicket.get().getSeatColumn()).isEqualTo('A');
    }

    @Test
    public void testFindAll() {
        // Create and save Tickets
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);

        // Retrieve all Tickets
        Iterable<Ticket> tickets = ticketRepository.findAll();

        // Assertions
        assertThat(tickets).hasSize(2);
    }

    @Test
    public void testDelete() {
        // Create and save a Ticket
        Ticket ticket = new Ticket();
        Ticket savedTicket = ticketRepository.save(ticket);

        // Delete the Ticket
        ticketRepository.deleteById(savedTicket.gettCode());

        // Verify the Ticket has been deleted
        Optional<Ticket> deletedTicket = ticketRepository.findById(savedTicket.gettCode());
        assertThat(deletedTicket).isNotPresent();
    }
}
