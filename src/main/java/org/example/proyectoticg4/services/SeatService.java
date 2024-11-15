package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.Seat;
import org.example.proyectoticg4.entities.SeatId;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat getSeatById(SeatId id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + id));
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(SeatId id) {
        if (!seatRepository.existsById(id)) {
            throw new ResourceNotFoundException("Seat not found with ID: " + id);
        }
        seatRepository.deleteById(id);
    }
}
