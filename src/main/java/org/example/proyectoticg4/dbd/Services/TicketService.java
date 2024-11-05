package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Ticket;
import org.example.proyectoticg4.dbd.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> findTicketsByUserAndReservation(String userId, Long reservationId) {
        return ticketRepository.findByUser_UserIdAndReservation_ReservationId(userId, reservationId);
    }

    public List<Ticket> getTicketsByUserId(String userId) {
        return ticketRepository.findByUser_UserId(userId);
    }
}
