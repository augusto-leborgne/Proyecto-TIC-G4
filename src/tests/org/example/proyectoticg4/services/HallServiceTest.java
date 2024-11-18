package org.example.proyectoticg4.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.proyectoticg4.entities.Hall;
import org.example.proyectoticg4.entities.HallId;
import org.example.proyectoticg4.repositories.HallRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HallServiceTest {

    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private HallService hallService;

    public HallServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllHalls_ShouldReturnListOfHalls() {
        when(hallRepository.findAll()).thenReturn(List.of(new Hall(), new Hall()));
        List<Hall> halls = hallService.getAllHalls();
        assertEquals(2, halls.size());
    }

    @Test
    void getHallById_ShouldReturnHall_WhenExists() {
        HallId hallId = new HallId(1, 1);
        Hall hall = new Hall();
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));
        Hall foundHall = hallService.getHallById(hallId);
        assertNotNull(foundHall);
    }

    @Test
    void getHallById_ShouldThrowException_WhenNotFound() {
        HallId hallId = new HallId(1, 1);
        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> hallService.getHallById(hallId));
    }

    @Test
    void createHall_ShouldSaveHall() {
        Hall hall = new Hall();
        when(hallRepository.save(hall)).thenReturn(hall);
        Hall createdHall = hallService.createHall(hall);
        assertNotNull(createdHall);
        verify(hallRepository, times(1)).save(hall);
    }
}
