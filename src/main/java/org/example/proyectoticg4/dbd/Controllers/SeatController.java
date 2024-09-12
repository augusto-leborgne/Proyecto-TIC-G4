package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Seat;
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

    @GetMapping("/{hallNumber}/{cinemaNumber}/{seatRow}/{seatColumn}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Integer hallNumber,
                                            @PathVariable Integer cinemaNumber,
                                            @PathVariable Integer seatRow,
                                            @PathVariable Integer seatColumn) {
        Seat.SeatId seatId = new Seat.SeatId();
        seatId.setHallNumber(hallNumber);
        seatId.setCinemaNumber(cinemaNumber);
        seatId.setSeatRow(seatRow);
        seatId.setSeatColumn(seatColumn);

        Optional<Seat> seat = seatService.getSeatById(seatId);
        return seat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }

    @DeleteMapping("/{hallNumber}/{cinemaNumber}/{seatRow}/{seatColumn}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Integer hallNumber,
                                           @PathVariable Integer cinemaNumber,
                                           @PathVariable Integer seatRow,
                                           @PathVariable Integer seatColumn) {
        Seat.SeatId seatId = new Seat.SeatId();
        seatId.setHallNumber(hallNumber);
        seatId.setCinemaNumber(cinemaNumber);
        seatId.setSeatRow(seatRow);
        seatId.setSeatColumn(seatColumn);

        seatService.deleteSeat(seatId);
        return ResponseEntity.noContent().build();
    }
}
