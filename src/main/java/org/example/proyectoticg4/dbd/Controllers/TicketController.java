package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Ticket;
import org.example.proyectoticg4.dbd.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping()
    public ResponseEntity<List<Ticket>> getTicketsByUserAndReservation(
            @RequestParam("userId") String userId,
            @RequestParam("reservationId") Long reservationId) {

        List<Ticket> tickets = ticketService.findTicketsByUserAndReservation(userId, reservationId);

        if (tickets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@PathVariable String userId) {
        List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
        if (tickets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tickets);
    }
}
