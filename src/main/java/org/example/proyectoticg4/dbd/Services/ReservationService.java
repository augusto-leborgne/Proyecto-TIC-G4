package org.example.proyectoticg4.dbd.Services;


import org.example.proyectoticg4.dbd.Entities.Reservation;
import org.example.proyectoticg4.dbd.Entities.Ticket;
import org.example.proyectoticg4.dbd.Repositories.ReservationRepository;
import org.example.proyectoticg4.dbd.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Reservation createReservation(Reservation reservation, List<Ticket> tickets) {
        // Save reservation first
        Reservation savedReservation = reservationRepository.save(reservation);

        // Set reservation to each ticket and save them
        for (Ticket ticket : tickets) {
            ticket.setReservation(savedReservation);
            ticketRepository.save(ticket);
        }

        return savedReservation;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }
}
