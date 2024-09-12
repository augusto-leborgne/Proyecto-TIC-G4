package org.example.proyectoticg4.dbd.Controllers;

import jakarta.persistence.EntityNotFoundException;
import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.HallId;
import org.example.proyectoticg4.dbd.Services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    /**
     * Get all halls.
     *
     * @return List of Hall entities
     */
    @GetMapping
    public ResponseEntity<List<Hall>> getAllHalls() {
        List<Hall> halls = hallService.getAllHalls();
        return ResponseEntity.ok(halls);
    }

    /**
     * Get a hall by its composite id (hallNumber and cinemaNumber).
     *
     * @param hallNumber The number of the hall
     * @param cinemaNumber The number of the cinema
     * @return The Hall entity if found
     */
    @GetMapping("/{hallNumber}/{cinemaNumber}")
    public ResponseEntity<Hall> getHallById(@PathVariable Integer hallNumber,
                                            @PathVariable Integer cinemaNumber) {
        HallId hallId = new HallId(hallNumber, cinemaNumber);
        Optional<Hall> hall = hallService.getHallById(hallId);
        return hall.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new hall.
     *
     * @param hall The Hall entity to be created
     * @return The created Hall entity
     */
    @PostMapping
    public ResponseEntity<Hall> createHall(@RequestBody Hall hall) {
        Hall createdHall = hallService.createHall(hall);
        return ResponseEntity.ok(createdHall);
    }

    /**
     * Update an existing hall's information.
     *
     * @param hallNumber The hall number
     * @param cinemaNumber The cinema number
     * @param updatedHall The Hall entity with updated information
     * @return The updated Hall entity
     */
    @PutMapping("/{hallNumber}/{cinemaNumber}")
    public ResponseEntity<Hall> updateHall(@PathVariable Integer hallNumber,
                                           @PathVariable Integer cinemaNumber,
                                           @RequestBody Hall updatedHall) {
        HallId hallId = new HallId(hallNumber, cinemaNumber);
        try {
            Hall updated = hallService.updateHall(hallId, updatedHall);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a hall by its id.
     *
     * @param hallNumber The hall number
     * @param cinemaNumber The cinema number
     * @return HTTP status representing the result of the delete operation
     */
    @DeleteMapping("/{hallNumber}/{cinemaNumber}")
    public ResponseEntity<Void> deleteHall(@PathVariable Integer hallNumber,
                                           @PathVariable Integer cinemaNumber) {
        HallId hallId = new HallId(hallNumber, cinemaNumber);
        try {
            hallService.deleteHall(hallId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
