package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Seat;
import org.example.proyectoticg4.dbd.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Optional<Seat> getSeatById(Seat.SeatId seatId) {
        return seatRepository.findById(seatId);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Seat.SeatId seatId) {
        seatRepository.deleteById(seatId);
    }
}
