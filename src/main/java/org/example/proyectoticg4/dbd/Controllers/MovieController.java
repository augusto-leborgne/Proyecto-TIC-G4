package org.example.proyectoticg4.dbd.Controllers;


import org.example.proyectoticg4.dbd.Entities.Movie;
import org.example.proyectoticg4.dbd.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Get all movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    // Get a movie by ID
    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String movieId) {
        Optional<Movie> movie = movieService.getMovieById(movieId);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new movie with a Base64 encoded image
    @PostMapping("/upload")
    public ResponseEntity<String> createMovie(@RequestBody Movie movie) {
        try {
            movieService.saveMovie(movie);
            return ResponseEntity.ok("Movie uploaded successfully with image");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Failed to upload movie");
        }
    }

    // Update an existing movie
    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieId, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(movieId, movie);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a movie
    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }
}
