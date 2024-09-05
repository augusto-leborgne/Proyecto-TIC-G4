package org.example.proyectoticg4.dbd.controllers;

import org.example.proyectoticg4.dbd.Entities.Cinema;
import org.example.proyectoticg4.dbd.Repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    @Autowired
    private CinemaRepository cinemaRepository;

    @GetMapping
    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cinema getCinemaById(@PathVariable Integer id) {
        return cinemaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Cinema createCinema(@RequestBody Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    @PutMapping("/{id}")
    public Cinema updateCinema(@PathVariable Integer id, @RequestBody Cinema cinema) {
        if (cinemaRepository.existsById(id)) {
            return cinemaRepository.save(cinema);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCinema(@PathVariable Integer id) {
        cinemaRepository.deleteById(id);
    }
}
