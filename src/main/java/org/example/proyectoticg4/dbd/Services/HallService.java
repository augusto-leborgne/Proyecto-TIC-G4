package org.example.proyectoticg4.dbd.Services;

import jakarta.persistence.EntityNotFoundException;
import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.HallId;
import org.example.proyectoticg4.dbd.Repositories.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {
    private final HallRepository hallRepository;

    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Hall getHallById(HallId hallId) {
        return hallRepository.findById(hallId)
                .orElseThrow(() -> new EntityNotFoundException("Hall not found with ID: " + hallId));
    }

    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }

    public Hall updateHall(HallId hallId, Hall updatedHall) {
        Hall existingHall = getHallById(hallId);
        existingHall.setCinema(updatedHall.getCinema());
        existingHall.setSeats(updatedHall.getSeats());
        return hallRepository.save(existingHall);
    }

    public void deleteHall(HallId hallId) {
        if (!hallRepository.existsById(hallId)) {
            throw new IllegalArgumentException("Hall with id " + hallId + " not found");
        }
        hallRepository.deleteById(hallId);
    }
}
