package org.example.proyectoticg4;

import org.example.proyectoticg4.entities.Movie;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.MovieRepository;
import org.example.proyectoticg4.services.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    public MovieServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMovies_shouldReturnListOfMovies() {
        List<Movie> mockMovies = List.of(new Movie());
        when(movieRepository.findAll()).thenReturn(mockMovies);

        List<Movie> result = movieService.getAllMovies();
        assertEquals(mockMovies.size(), result.size());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void getMovieById_shouldReturnMovie() {
        Movie mockMovie = new Movie();
        when(movieRepository.findById("1")).thenReturn(Optional.of(mockMovie));

        Movie result = movieService.getMovieById("1");
        assertNotNull(result);
        verify(movieRepository, times(1)).findById("1");
    }

    @Test
    void getMovieById_whenNotFound_shouldThrowException() {
        when(movieRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieById("1"));
        verify(movieRepository, times(1)).findById("1");
    }

    @Test
    void saveMovie_shouldSaveMovie() {
        Movie mockMovie = new Movie();
        when(movieRepository.save(mockMovie)).thenReturn(mockMovie);

        movieService.saveMovie(mockMovie);
        verify(movieRepository, times(1)).save(mockMovie);
    }

    @Test
    void deleteMovie_shouldDeleteMovie() {
        when(movieRepository.existsById("1")).thenReturn(true);

        movieService.deleteMovie("1");
        verify(movieRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteMovie_whenNotFound_shouldThrowException() {
        when(movieRepository.existsById("1")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> movieService.deleteMovie("1"));
    }
}
