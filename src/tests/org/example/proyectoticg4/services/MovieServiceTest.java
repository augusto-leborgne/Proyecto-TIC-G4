package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.Movie;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    public MovieServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMovies_ShouldReturnListOfMovies() {
        when(movieRepository.findAll()).thenReturn(List.of(new Movie(), new Movie()));
        List<Movie> movies = movieService.getAllMovies();
        assertEquals(2, movies.size());
    }

    @Test
    void getMovieById_ShouldReturnMovie_WhenExists() {
        Movie movie = new Movie();
        when(movieRepository.findById("M1")).thenReturn(Optional.of(movie));
        Movie foundMovie = movieService.getMovieById("M1");
        assertNotNull(foundMovie);
    }

    @Test
    void getMovieById_ShouldThrowException_WhenNotFound() {
        when(movieRepository.findById("M1")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieById("M1"));
    }

    @Test
    void saveMovie_ShouldSaveMovie() {
        Movie movie = new Movie();
        when(movieRepository.save(movie)).thenReturn(movie);
        movieService.saveMovie(movie);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void deleteMovie_ShouldDelete_WhenMovieExists() {
        when(movieRepository.existsById("M1")).thenReturn(true);
        movieService.deleteMovie("M1");
        verify(movieRepository, times(1)).deleteById("M1");
    }

    @Test
    void deleteMovie_ShouldThrowException_WhenMovieNotFound() {
        when(movieRepository.existsById("M1")).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> movieService.deleteMovie("M1"));
    }

    @Test
    void updateMovie_ShouldUpdateMovie_WhenExists() {
        Movie existingMovie = new Movie();
        existingMovie.setMovieId("M1");

        Movie updatedMovie = new Movie();
        updatedMovie.setDuration(120);

        when(movieRepository.findById("M1")).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(existingMovie)).thenReturn(existingMovie);

        Movie result = movieService.updateMovie("M1", updatedMovie);
        assertEquals(120, result.getDuration());
        verify(movieRepository, times(1)).save(existingMovie);
    }
}
