package org.example.proyectoticg4;

import org.example.proyectoticg4.entities.Cinema;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.CinemaRepository;
import org.example.proyectoticg4.services.CinemaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CinemaServiceTest {

    @InjectMocks
    private CinemaService cinemaService;

    @Mock
    private CinemaRepository cinemaRepository;

    public CinemaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCinemas_shouldReturnListOfCinemas() {
        List<Cinema> mockCinemas = List.of(new Cinema());
        when(cinemaRepository.findAll()).thenReturn(mockCinemas);

        List<Cinema> result = cinemaService.getAllCinemas();
        assertEquals(mockCinemas.size(), result.size());
        verify(cinemaRepository, times(1)).findAll();
    }

    @Test
    void getCinemaById_shouldReturnCinema() {
        Cinema mockCinema = new Cinema();
        when(cinemaRepository.findById(1)).thenReturn(Optional.of(mockCinema));

        Cinema result = cinemaService.getCinemaById(1);
        assertNotNull(result);
        verify(cinemaRepository, times(1)).findById(1);
    }

    @Test
    void getCinemaById_whenNotFound_shouldThrowException() {
        when(cinemaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cinemaService.getCinemaById(1));
        verify(cinemaRepository, times(1)).findById(1);
    }

    @Test
    void createCinema_shouldSaveAndReturnCinema() {
        Cinema mockCinema = new Cinema();
        when(cinemaRepository.save(mockCinema)).thenReturn(mockCinema);

        Cinema result = cinemaService.createCinema(mockCinema);
        assertEquals(mockCinema, result);
        verify(cinemaRepository, times(1)).save(mockCinema);
    }

    @Test
    void updateCinema_shouldUpdateAndReturnCinema() {
        Cinema existingCinema = new Cinema();
        Cinema updatedDetails = new Cinema();
        updatedDetails.setNeighborhood("Updated");
        when(cinemaRepository.findById(1)).thenReturn(Optional.of(existingCinema));
        when(cinemaRepository.save(existingCinema)).thenReturn(existingCinema);

        Cinema result = cinemaService.updateCinema(1, updatedDetails);
        assertEquals("Updated", result.getNeighborhood());
        verify(cinemaRepository, times(1)).save(existingCinema);
    }

    @Test
    void deleteCinema_shouldDeleteCinema() {
        Cinema mockCinema = new Cinema();
        when(cinemaRepository.findById(1)).thenReturn(Optional.of(mockCinema));

        cinemaService.deleteCinema(1);
        verify(cinemaRepository, times(1)).delete(mockCinema);
    }
}
