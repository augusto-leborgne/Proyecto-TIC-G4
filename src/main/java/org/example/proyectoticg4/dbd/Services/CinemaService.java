package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Cinema;
import org.example.proyectoticg4.dbd.Exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.dbd.Repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinema getCinemaById(int id) {
        return cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with ID " + id + " not found"));
    }

    public Cinema createCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public Cinema updateCinema(int id, Cinema cinemaDetails) {
        Cinema existingCinema = getCinemaById(id);
        existingCinema.setNeighborhood(cinemaDetails.getNeighborhood());
        existingCinema.setHalls(cinemaDetails.getHalls());
        return cinemaRepository.save(existingCinema);
    }

    public void deleteCinema(int id) {
        Cinema existingCinema = getCinemaById(id);
        cinemaRepository.delete(existingCinema);
    }
}
