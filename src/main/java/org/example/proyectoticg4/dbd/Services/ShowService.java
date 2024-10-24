package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Show;
import org.example.proyectoticg4.dbd.Repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> findShowsByCinemaNumber(int cinemaNumber) {
        return showRepository.findShowsByCinemaNumber(cinemaNumber);
    }

    public Show createShow(Show show) {
        return showRepository.save(show);
    }

    public void deleteShow(Integer showId) {
        showRepository.deleteById(showId);
    }
}
