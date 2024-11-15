package org.example.proyectoticg4.controllers;

import jakarta.validation.Valid;
import org.example.proyectoticg4.entities.Hall;
import org.example.proyectoticg4.entities.Movie;
import org.example.proyectoticg4.entities.Show;
import org.example.proyectoticg4.entities.ShowSeatAvailability;
import org.example.proyectoticg4.services.HallService;
import org.example.proyectoticg4.services.MovieService;
import org.example.proyectoticg4.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    @GetMapping("/cinema/{cinemaNumber}")
    public List<Movie> getMoviesByCinemaNumber(@PathVariable int cinemaNumber) {
        List<Show> shows = showService.findShowsByCinemaNumber(cinemaNumber);
        return shows.stream()
                .map(Show::getMovie)
                .distinct()
                .toList();
    }

    @GetMapping("/showtimes")
    public List<LocalDateTime> getShowtimesByMovieAndCinema(
            @RequestParam("movieId") String movieId,
            @RequestParam("cinemaNumber") Integer cinemaNumber) {

        if (movieId == null || cinemaNumber == null) {
            throw new IllegalArgumentException("Movie ID and Cinema Number must be provided.");
        }
        List<Show> shows = showService.findShowsByMovieAndCinema(movieId, cinemaNumber);

        return shows.stream()
                .map(Show::getShowTime)
                .toList();
    }

    @GetMapping("/seats")
    public List<ShowSeatAvailability> getShowSeats(
            @RequestParam("movieId") String movieId,
            @RequestParam("cinemaNumber") Integer cinemaNumber,
            @RequestParam("showTime") LocalDateTime showTime) {

        if (movieId == null || cinemaNumber == null || showTime == null) {
            throw new IllegalArgumentException("Movie ID, Cinema Number and Show Time must be provided.");
        }
        return showService.findSeats(movieId, cinemaNumber, showTime);
    }

    @PostMapping
    public String createShow(@Valid @RequestBody Show show) {
        Movie existingMovie = movieService.getMovieById(show.getMovie().getMovieId());
        Hall existingHall = hallService.getHallById(show.getHall().getHallId());

        show.setMovie(existingMovie);
        show.setHall(existingHall);

        Show savedShow = showService.createShowWithAvailableSeats(
                existingMovie,
                existingHall,
                show.getShowTime(),
                show.getPrice()
        );

        return "Show created with ID: " + savedShow.getShowCode();
    }

    @DeleteMapping("/{id}")
    public void deleteShow(@PathVariable Integer id) {
        showService.deleteShow(id);
    }

    @DeleteMapping("/all")
    public void deleteAllShows() {
        showService.deleteAllShows();
    }
}
