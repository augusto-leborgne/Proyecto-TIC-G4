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


    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(String movieId) {
        return movieRepository.findById(movieId);
    }

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteMovie(String movieId) {
        movieRepository.deleteById(movieId);
    }

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

    public List<byte[]> getAllImages() {
        return movieRepository.findAllImages();
    }

}
