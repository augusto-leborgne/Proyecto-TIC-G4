package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.Cinema;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.CinemaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CinemaServiceTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private CinemaService cinemaService;

    public CinemaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCinemas_ShouldReturnListOfCinemas() {
        when(cinemaRepository.findAll()).thenReturn(List.of(new Cinema(), new Cinema()));
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        assertEquals(2, cinemas.size());
    }

    @Test
    void getCinemaById_ShouldReturnCinema_WhenExists() {
        Cinema cinema = new Cinema();
        when(cinemaRepository.findById(1)).thenReturn(Optional.of(cinema));
        Cinema foundCinema = cinemaService.getCinemaById(1);
        assertNotNull(foundCinema);
    }

    @Test
    void getCinemaById_ShouldThrowException_WhenNotFound() {
        when(cinemaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> cinemaService.getCinemaById(1));
    }

    @Test
    void createCinema_ShouldSaveCinema() {
        Cinema cinema = new Cinema();
        when(cinemaRepository.save(cinema)).thenReturn(cinema);
        Cinema createdCinema = cinemaService.createCinema(cinema);
        assertNotNull(createdCinema);
        verify(cinemaRepository, times(1)).save(cinema);
    }

    @Test
    void deleteCinema_ShouldDelete_WhenCinemaExists() {
        Cinema cinema = new Cinema();
        when(cinemaRepository.findById(1)).thenReturn(Optional.of(cinema));
        cinemaService.deleteCinema(1);
        verify(cinemaRepository, times(1)).delete(cinema);
    }

    @Test
    void deleteCinema_ShouldThrowException_WhenCinemaNotFound() {
        when(cinemaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> cinemaService.deleteCinema(1));
    }
}
