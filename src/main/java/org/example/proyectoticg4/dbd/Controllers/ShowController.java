package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.Movie;
import org.example.proyectoticg4.dbd.Entities.Show;
import org.example.proyectoticg4.dbd.Services.HallService;
import org.example.proyectoticg4.dbd.Services.MovieService;
import org.example.proyectoticg4.dbd.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;
    private final MovieService movieService;
    private final HallService hallService;

    @Autowired
    public ShowController(ShowService showService, MovieService movieService, HallService hallService) {
        this.showService = showService;
        this.movieService = movieService;
        this.hallService = hallService;
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        List<Show> shows = showService.getAllShows();
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/cinema/{cinemaNumber}")
    public ResponseEntity<List<Movie>> getMoviesByCinemaNumber(@PathVariable int cinemaNumber) {
        List<Show> shows = showService.findShowsByCinemaNumber(cinemaNumber);

        if (shows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Movie> moviesList = new ArrayList<>();

        for (Show show : shows) {
            moviesList.add(show.getMovie());
        }

        return ResponseEntity.ok(moviesList);
    }

    @PostMapping
    public ResponseEntity<String> createShow(@RequestBody Show show) {
        // Fetch the movie from the database by its name
        Optional<Movie> existingMovie = movieService.getMovieById(show.getMovie().getMovieId());
        if (existingMovie.isEmpty()) {
            return ResponseEntity.badRequest().body("Movie not found");
        }

        // Fetch the hall from the database using its cinemaNumber and hallNumber
        Optional<Hall> existingHall = hallService.getHallById(
                show.getHall().getHallId()
        );
        if (existingHall.isEmpty()) {
            return ResponseEntity.badRequest().body("Hall not found");
        }

        // Set the existing movie and hall to the show object
        show.setMovie(existingMovie.get());
        show.setHall(existingHall.get());

        // Save the show
        Show savedShow = showService.createShow(show);
        return ResponseEntity.ok(savedShow.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteShow(@PathVariable Integer id) {
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }
}
