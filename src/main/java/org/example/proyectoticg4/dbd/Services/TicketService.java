package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Ticket;
import org.example.proyectoticg4.dbd.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long code) {
        return ticketRepository.findById(code);
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long code) {
        ticketRepository.deleteById(code);
    }
}
