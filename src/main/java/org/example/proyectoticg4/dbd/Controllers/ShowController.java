package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.Movie;
import org.example.proyectoticg4.dbd.Entities.Show;
import org.example.proyectoticg4.dbd.Entities.ShowSeatAvailability;
import org.example.proyectoticg4.dbd.Services.HallService;
import org.example.proyectoticg4.dbd.Services.MovieService;
import org.example.proyectoticg4.dbd.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/showtimes")
    public ResponseEntity<List<LocalDateTime>> getShowtimesByMovieAndCinema(
            @RequestParam("movieId") String movieId,
            @RequestParam("cinemaNumber") Integer cinemaNumber) {

        if (movieId == null || cinemaNumber == null) {
            throw new IllegalArgumentException("Movie ID and Cinema Number must be provided.");
        }
        // Fetch shows by movie and cinema
        List<Show> shows = showService.findShowsByMovieAndCinema(movieId, cinemaNumber);

        // Check if any shows are found
        if (shows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Extract showtimes from shows
        List<LocalDateTime> showtimes = shows.stream()
                .map(Show::getShowTime)
                .toList();

        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/seats")
    public ResponseEntity<List<ShowSeatAvailability>> getShowCode(
            @RequestParam("movieId") String movieId,
            @RequestParam("cinemaNumber") Integer cinemaNumber,
            @RequestParam("showTime") LocalDateTime showTime) {

        if (movieId == null || cinemaNumber == null || showTime == null) {
            throw new IllegalArgumentException("Movie ID, Cinema Number and Show Time must be provided.");
        }
        // Fetch shows by movie, cinema and show time
        List<ShowSeatAvailability> seats = showService.findSeats(movieId, cinemaNumber, showTime);

        // Check if any shows are found
        if (seats == null) {
            return ResponseEntity.notFound().build();
        }



        return ResponseEntity.ok(seats);
    }

    @PostMapping
    public ResponseEntity<String> createShow(@RequestBody Show show) {
        try {
            // Fetch the movie from the database by its ID
            Optional<Movie> existingMovie = movieService.getMovieById(show.getMovie().getMovieId());
            if (existingMovie.isEmpty()) {
                return ResponseEntity.badRequest().body("Movie not found");
            }

            // Fetch the hall from the database by its ID
            Optional<Hall> existingHall = hallService.getHallById(show.getHall().getHallId());
            if (existingHall.isEmpty()) {
                return ResponseEntity.badRequest().body("Hall not found");
            }

            // Set the fetched movie and hall in the show object
            show.setMovie(existingMovie.get());
            show.setHall(existingHall.get());

            // Create show with available seats
            Show savedShow = showService.createShowWithAvailableSeats(existingMovie.get(), existingHall.get(), show.getShowTime());

            return ResponseEntity.ok("Show created with ID: " + savedShow.getShowCode());
        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace for debugging
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteShow(@PathVariable Integer id) {
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }
}
