package org.example.proyectoticg4.controllers;

import org.example.proyectoticg4.entities.Seat;
import org.example.proyectoticg4.entities.SeatId;
import org.example.proyectoticg4.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{hallNumber}/{cinemaNumber}/{seatColumn}/{seatRow}")
    public Seat getSeatById(@PathVariable int hallNumber,
                            @PathVariable int cinemaNumber,
                            @PathVariable int seatColumn,
                            @PathVariable int seatRow) {
        SeatId seatId = new SeatId(hallNumber, cinemaNumber, seatColumn, seatRow);
        return seatService.getSeatById(seatId);
    }

    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }

    @DeleteMapping("/{hallNumber}/{cinemaNumber}/{seatColumn}/{seatRow}")
    public void deleteSeat(@PathVariable int hallNumber,
                           @PathVariable int cinemaNumber,
                           @PathVariable int seatColumn,
                           @PathVariable int seatRow) {
        SeatId seatId = new SeatId(hallNumber, cinemaNumber, seatColumn, seatRow);
        seatService.deleteSeat(seatId);
    }
}
