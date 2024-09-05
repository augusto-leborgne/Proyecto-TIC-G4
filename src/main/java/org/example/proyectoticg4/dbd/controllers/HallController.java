package org.example.proyectoticg4.dbd.controllers;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.repositories.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    @Autowired
    private HallRepository hallRepository;

    @GetMapping
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    @GetMapping("/{cinemaNumber}/{hallNumber}")
    public Hall getHallById(@PathVariable Integer cinemaNumber, @PathVariable Integer hallNumber) {
        Hall.HallId hallId = new Hall.HallId();
        hallId.setCinemaNumber(cinemaNumber);
        hallId.setHNumber(hallNumber);
        return hallRepository.findById(hallId).orElse(null);
    }

    @PostMapping
    public Hall createHall(@RequestBody Hall hall) {
        return hallRepository.save(hall);
    }

    @DeleteMapping("/{cinemaNumber}/{hallNumber}")
    public void deleteHall(@PathVariable Integer cinemaNumber, @PathVariable Integer hallNumber) {
        Hall.HallId hallId = new Hall.HallId();
        hallId.setCinemaNumber(cinemaNumber);
        hallId.setHNumber(hallNumber);
        hallRepository.deleteById(hallId);
    }
}
