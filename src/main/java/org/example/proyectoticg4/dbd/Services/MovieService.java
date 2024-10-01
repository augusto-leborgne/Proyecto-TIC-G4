package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Movie;
import org.example.proyectoticg4.dbd.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Get all movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get a single movie by its name (primary key)
    public Optional<Movie> getMovieById(String movieId) {
        return movieRepository.findById(movieId);
    }

    // Save a movie
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Delete a movie by its name
    public void deleteMovie(String movieId) {
        movieRepository.deleteById(movieId);
    }

    // Update a movie
    public Movie updateMovie(String movieId, Movie updatedMovie) {
        Optional<Movie> existingMovie = movieRepository.findById(movieId);
        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get();
            movie.setDuration(updatedMovie.getDuration());
            movie.setDirector(updatedMovie.getDirector());
            movie.setMinimumAge(updatedMovie.getMinimumAge());
            return movieRepository.save(movie);
        } else {
            return null;
        }
    }
}
