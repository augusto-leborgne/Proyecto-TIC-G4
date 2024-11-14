package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Movie;
import org.example.proyectoticg4.dbd.Exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.dbd.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));
    }

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteMovie(String movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie with ID " + movieId + " not found");
        }
        movieRepository.deleteById(movieId);
    }

    public Movie updateMovie(String movieId, Movie updatedMovie) {
        Movie existingMovie = getMovieById(movieId);
        existingMovie.setDuration(updatedMovie.getDuration());
        existingMovie.setDirector(updatedMovie.getDirector());
        existingMovie.setMinimumAge(updatedMovie.getMinimumAge());
        existingMovie.setImage(updatedMovie.getImage());
        return movieRepository.save(existingMovie);
    }

    public List<byte[]> getAllImages() {
        return movieRepository.findAllImages();
    }
}
