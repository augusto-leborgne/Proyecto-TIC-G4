package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Movie;
import org.example.proyectoticg4.dbd.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

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
    public Movie getMovieById(@PathVariable String movieId) {
        return movieService.getMovieById(movieId);
    }

    @PostMapping("/upload")
    public String createMovie(@Valid @RequestBody Movie movie) {
        movieService.saveMovie(movie);
        return "Movie uploaded successfully with image";
    }

    @PutMapping("/{movieId}")
    public Movie updateMovie(@PathVariable String movieId, @Valid @RequestBody Movie movie) {
        return movieService.updateMovie(movieId, movie);
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable String movieId) {
        movieService.deleteMovie(movieId);
    }

    @GetMapping("/image")
    public List<byte[]> getAllImages() {
        return movieService.getAllImages();
    }
}
