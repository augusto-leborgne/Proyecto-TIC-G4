package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.*;
import org.example.proyectoticg4.dbd.Services.ReservationService;
import org.example.proyectoticg4.dbd.Services.ShowSeatAvailabilityService;
import org.example.proyectoticg4.dbd.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@RequestParam("userId") String userId) {
        List<Reservation> reservations = reservationService.findReservationsByUserId(userId);

        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(
            @RequestParam String userId,
            @RequestBody List<ShowSeatAvailability> selectedSeats) {

        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (selectedSeats.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Reservation reservation = reservationService.createReservationWithTickets(user, selectedSeats);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(@RequestParam Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        reservationService.deleteReservation(reservation);
        return ResponseEntity.ok().build();
    }
}
