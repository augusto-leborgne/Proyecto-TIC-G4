package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.Cinema;
import org.example.proyectoticg4.entities.Hall;
import org.example.proyectoticg4.entities.HallId;
import org.example.proyectoticg4.repositories.HallRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HallServiceTest {

    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private HallService hallService;

    @Test
    public void testGetAllHalls() {
        // Datos de prueba
        Hall hall1 = new Hall();
        Hall hall2 = new Hall();
        List<Hall> hallList = Arrays.asList(hall1, hall2);

        // Configuración del mock
        when(hallRepository.findAll()).thenReturn(hallList);

        // Ejecución del método a probar
        List<Hall> result = hallService.getAllHalls();

        // Verificaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(hallRepository, times(1)).findAll();
    }

    @Test
    public void testGetHallById_Found() {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);
        Hall hall = new Hall();
        hall.setId(hallId);

        // Configuración del mock
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));

        // Ejecución del método a probar
        Hall result = hallService.getHallById(hallId);

        // Verificaciones
        assertNotNull(result);
        assertEquals(hallId, result.getId());
        verify(hallRepository, times(1)).findById(hallId);
    }

    @Test
    public void testGetHallById_NotFound() {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);

        // Configuración del mock
        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        // Ejecución y verificación de la excepción
        assertThrows(EntityNotFoundException.class, () -> {
            hallService.getHallById(hallId);
        });

        verify(hallRepository, times(1)).findById(hallId);
    }

    @Test
    public void testCreateHall() {
        // Datos de prueba
        Hall hall = new Hall();

        // Configuración del mock
        when(hallRepository.save(hall)).thenReturn(hall);

        // Ejecución del método a probar
        Hall result = hallService.createHall(hall);

        // Verificaciones
        assertNotNull(result);
        verify(hallRepository, times(1)).save(hall);
    }

    @Test
    public void testUpdateHall_Found() {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);
        Hall existingHall = new Hall();
        existingHall.setId(hallId);
        existingHall.setSeats(100);

        Hall updatedHall = new Hall();
        updatedHall.setCinema(new Cinema()); // Suponiendo que tienes una entidad Cinema
        updatedHall.setSeats(150);

        // Configuración del mock
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(existingHall));
        when(hallRepository.save(existingHall)).thenReturn(existingHall);

        // Ejecución del método a probar
        Hall result = hallService.updateHall(hallId, updatedHall);

        // Verificaciones
        assertNotNull(result);
        assertEquals(150, result.getSeats());
        verify(hallRepository, times(1)).findById(hallId);
        verify(hallRepository, times(1)).save(existingHall);
    }

    @Test
    public void testUpdateHall_NotFound() {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);
        Hall updatedHall = new Hall();

        // Configuración del mock
        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        // Ejecución y verificación de la excepción
        assertThrows(EntityNotFoundException.class, () -> {
            hallService.updateHall(hallId, updatedHall);
        });

        verify(hallRepository, times(1)).findById(hallId);
        verify(hallRepository, never()).save(any(Hall.class));
    }

    @Test
    public void testDeleteHall_Found() {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);

        // Configuración del mock
        when(hallRepository.existsById(hallId)).thenReturn(true);

        // Ejecución del método a probar
        hallService.deleteHall(hallId);

        // Verificaciones
        verify(hallRepository, times(1)).existsById(hallId);
        verify(hallRepository, times(1)).deleteById(hallId);
    }

    @Test
    public void testDeleteHall_NotFound() {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);

        // Configuración del mock
        when(hallRepository.existsById(hallId)).thenReturn(false);

        // Ejecución y verificación de la excepción
        assertThrows(IllegalArgumentException.class, () -> {
            hallService.deleteHall(hallId);
        });

        verify(hallRepository, times(1)).existsById(hallId);
        verify(hallRepository, never()).deleteById(any(HallId.class));
    }
}
