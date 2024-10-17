package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Ticket;
import org.example.proyectoticg4.dbd.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        // Ensure that the user is set in the ticket object
        if (ticket.getUser() == null || ticket.getUser().getUserId() == null){
            return ResponseEntity.badRequest().body(null); // Handle the case where user is not provided
        }

        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.ok(createdTicket);
    }
}
