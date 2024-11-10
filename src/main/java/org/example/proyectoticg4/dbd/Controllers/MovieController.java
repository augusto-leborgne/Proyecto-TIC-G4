package org.example.proyectoticg4.dbd.Controllers;


import org.example.proyectoticg4.dbd.Entities.Movie;
import org.example.proyectoticg4.dbd.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String movieId) {
        Optional<Movie> movie = movieService.getMovieById(movieId);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> createMovie(@RequestBody Movie movieDTO) {
        try {
            Movie movie = new Movie();
            movie.setMovieId(movieDTO.getMovieId());
            movie.setDuration(movieDTO.getDuration());
            movie.setDirector(movieDTO.getDirector());
            movie.setMinimumAge(movieDTO.getMinimumAge());
            movie.setImage(movieDTO.getImage());

            movieService.saveMovie(movie);
            return ResponseEntity.ok("Movie uploaded successfully with image");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload movie: " + e.getMessage());
        }
    }


    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieId, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(movieId, movie);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image")
    public List<byte[]> getAllImages() {
        return movieService.getAllImages();
    }
}
