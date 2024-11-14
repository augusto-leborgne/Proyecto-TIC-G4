package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.*;
import org.example.proyectoticg4.dbd.Exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.dbd.Repositories.ReservationRepository;
import org.example.proyectoticg4.dbd.Repositories.ShowSeatAvailabilityRepository;
import org.example.proyectoticg4.dbd.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        if (selectedSeats.isEmpty()) {
            throw new IllegalArgumentException("No seats selected for reservation");
        }

        Show show = selectedSeats.get(0).getShow();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShow(show);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setTotal(selectedSeats.size() * show.getPrice());

        List<Ticket> tickets = new ArrayList<>();

        for (ShowSeatAvailability seat : selectedSeats) {
            if (!seat.getAvailable()) {
                throw new IllegalArgumentException("Seat is already reserved: " + seat.getId());
            }

            Ticket ticket = getTicket(seat, reservation);

            ticketRepository.save(ticket);

            tickets.add(ticket);

            seat.setAvailable(false);
            showSeatAvailabilityRepository.save(seat);
        }

        reservation.setTickets(tickets);
        return reservationRepository.save(reservation);
    }

    private static Ticket getTicket(ShowSeatAvailability seat, Reservation reservation) {
        Ticket ticket = new Ticket();
        ticket.setReservation(reservation);
        ticket.setSeatColumn(seat.getId().getSeatColumn());
        ticket.setSeatRow(seat.getId().getSeatRow());
        ticket.setHallNumber(seat.getShow().getHall().getHallId().gethNumber());
        ticket.setCinemaNumber(seat.getShow().getHall().getCinema().getCiNumber());
        ticket.setShowTime(seat.getShow().getShowTime());
        ticket.setMovieName(seat.getShow().getMovie().getMovieId());
        return ticket;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " + id + " not found"));
    }

    public void deleteReservation(Reservation reservation) {
        List<Ticket> tickets = reservation.getTickets();

        for (Ticket ticket : tickets) {
            ShowSeatAvailabilityId seatId = new ShowSeatAvailabilityId(
                    reservation.getShow().getShowCode(),
                    new SeatId(ticket.getHallNumber(), ticket.getCinemaNumber(), ticket.getSeatColumn(), ticket.getSeatRow())
            );

            ShowSeatAvailability seat = showSeatAvailabilityRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found for ticket: " + ticket.gettCode()));

            seat.setAvailable(true);
            showSeatAvailabilityRepository.save(seat);
        }

        reservationRepository.delete(reservation);
    }
}
