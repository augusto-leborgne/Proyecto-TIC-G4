package org.example.proyectoticg4.dbd.Services;

import jakarta.persistence.EntityNotFoundException;
import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.HallId;
import org.example.proyectoticg4.dbd.Repositories.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {

    private final HallRepository hallRepository;

    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    /**
     * Retrieve all halls from the database.
     *
     * @return List of Hall entities
     */
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    /**
     * Retrieve a specific hall by its id.
     *
     * @param hallId The id of the hall
     * @return An optional Hall entity
     */
    public Optional<Hall> getHallById(HallId hallId) {
        return hallRepository.findById(hallId);
    }

    /**
     * Create a new hall in the database.
     *
     * @param hall The Hall entity to be created
     * @return The created Hall entity
     */
    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }

    /**
     * Update an existing hall's information.
     *
     * @param hallId      The id of the hall to be updated
     * @param updatedHall The updated Hall entity
     * @return The updated Hall entity
     */
    public Hall updateHall(HallId hallId, Hall updatedHall) {
        return hallRepository.findById(hallId).map(existingHall -> {
            // Update the existing hall's properties
            existingHall.setCinema(updatedHall.getCinema());
            existingHall.setSeats(updatedHall.getSeats());
            return hallRepository.save(existingHall);
        }).orElseThrow(() -> new EntityNotFoundException("Hall not found!"));
    }

    /**
     * Delete a hall by its id.
     *
     * @param hallId The id of the hall to be deleted
     */
    public void deleteHall(HallId hallId) {
        if (!hallRepository.existsById(hallId)) {
            throw new IllegalArgumentException("Hall with id " + hallId + " not found");
        }
        hallRepository.deleteById(hallId);
    }
}
