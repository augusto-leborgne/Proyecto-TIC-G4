package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Seat;
import org.example.proyectoticg4.dbd.Entities.SeatId;
import org.example.proyectoticg4.dbd.Services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{hallNumber}/{hallCinemaNumber}/{seatColumn}/{seatRow}")
    public ResponseEntity<Seat> getSeatById(@PathVariable int hallNumber,
                                            @PathVariable int hallCinemaNumber,
                                            @PathVariable int seatColumn,
                                            @PathVariable int seatRow) {
        SeatId seatId = new SeatId(hallNumber, hallCinemaNumber, seatColumn, seatRow);
        Optional<Seat> seat = seatService.getSeatById(seatId);
        return seat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }

    @DeleteMapping("/{hallNumber}/{hallCinemaNumber}/{seatColumn}/{seatRow}")
    public ResponseEntity<Void> deleteSeat(@PathVariable int hallNumber,
                                           @PathVariable int hallCinemaNumber,
                                           @PathVariable int seatColumn,
                                           @PathVariable int seatRow) {
        SeatId seatId = new SeatId(hallNumber, hallCinemaNumber, seatColumn, seatRow);
        seatService.deleteSeat(seatId);
        return ResponseEntity.noContent().build();
    }
}
