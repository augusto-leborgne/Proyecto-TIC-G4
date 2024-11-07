package org.example.proyectoticg4.dbd.Services;


import org.example.proyectoticg4.dbd.Entities.*;
import org.example.proyectoticg4.dbd.Repositories.ReservationRepository;
import org.example.proyectoticg4.dbd.Repositories.ShowSeatAvailabilityRepository;
import org.example.proyectoticg4.dbd.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;


    public Reservation createReservationWithTickets(User user, List<ShowSeatAvailability> selectedSeats) {
        // 1. Create the Reservation
        Show show = selectedSeats.getFirst().getShow();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShow(show); // Assume all seats are for the same show
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setTotal(selectedSeats.size() * show.getPrice());
        reservation = reservationRepository.save(reservation);

        // 2. Create Tickets for each selected seat and update seat availability
        for (ShowSeatAvailability seat : selectedSeats) {
            // Create a new Ticket
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setShow(seat.getShow());
            ticket.setReservation(reservation);
            ticket.setSeatColumn(seat.getId().getSeatColumn());
            ticket.setSeatRow(seat.getId().getSeatRow());
            ticket.setHallNumber(seat.getShow().getHall().getHallId().gethNumber());
            ticket.setCinemaNumber(seat.getShow().getHall().getCinema().getCiNumber());
            ticket.setShowTime(seat.getShow().getShowTime());

            // Save ticket
            ticketRepository.save(ticket);

            // Mark seat as unavailable
            seat.setAvailable(false);
            showSeatAvailabilityRepository.save(seat);
        }

        return reservation;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> findReservationsByUserId(String userId) {
        return reservationRepository.findByUser_UserId(userId);
    }

    public void deleteReservation(Reservation reservation) {
        List<Ticket> tickets = reservation.getTickets();

        for (Ticket ticket : tickets) {
            ShowSeatAvailability seat = showSeatAvailabilityRepository.findById(
                            new ShowSeatAvailabilityId(ticket.getShow().getShowCode(), new SeatId(0,0,ticket.getSeatColumn(), ticket.getSeatRow())))
                    .orElseThrow(() -> new RuntimeException("Seat not found")); // SeatId con hall y cine 0 porque solo se precisa columna y fila

            seat.setAvailable(false);
            showSeatAvailabilityRepository.save(seat);
        }

        reservationRepository.delete(reservation);
    }
}
