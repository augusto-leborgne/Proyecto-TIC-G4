package org.example.proyectoticg4.controllers;

import jakarta.validation.Valid;
import org.example.proyectoticg4.entities.Reservation;
import org.example.proyectoticg4.entities.ShowSeatAvailabilityId;
import org.example.proyectoticg4.services.ReservationService;
import org.example.proyectoticg4.services.ShowSeatAvailabilityService;
import org.example.proyectoticg4.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShowSeatAvailabilityService showSeatAvailabilityService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping()
    public Reservation createReservation(
            @RequestParam String userId,
            @Valid @RequestBody List<ShowSeatAvailabilityId> selectedSeatsId) {

        var user = userService.getUserById(userId);
        var selectedSeats = showSeatAvailabilityService.findSeatsByIds(selectedSeatsId);

        return reservationService.createReservationWithTickets(user, selectedSeats);
    }

    @DeleteMapping()
    public void deleteReservation(@RequestParam Long reservationId) {
        var reservation = reservationService.getReservationById(reservationId);
        reservationService.deleteReservation(reservation);
    }
}
