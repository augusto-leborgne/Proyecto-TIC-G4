package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.Seat;
import org.example.proyectoticg4.entities.SeatId;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    public void testGetAllSeats() {
        // Datos de prueba
        Seat seat1 = new Seat();
        Seat seat2 = new Seat();
        List<Seat> seats = Arrays.asList(seat1, seat2);

        // Configuración del mock
        when(seatRepository.findAll()).thenReturn(seats);

        // Ejecución del método a probar
        List<Seat> result = seatService.getAllSeats();

        // Verificaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(seatRepository, times(1)).findAll();
    }

    @Test
    public void testGetSeatById_Found() {
        // Datos de prueba
        SeatId seatId = new SeatId(1, 1, 'A', 1);
        Seat seat = new Seat();
        seat.setId(seatId);

        // Configuración del mock
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        // Ejecución del método a probar
        Seat result = seatService.getSeatById(seatId);

        // Verificaciones
        assertNotNull(result);
        assertEquals(seatId, result.getId());
        verify(seatRepository, times(1)).findById(seatId);
    }

    @Test
    public void testGetSeatById_NotFound() {
        // Datos de prueba
        SeatId seatId = new SeatId(1, 1, 'A', 1);

        // Configuración del mock
        when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        // Ejecución y verificación de la excepción
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            seatService.getSeatById(seatId);
        });

        assertEquals("Seat not found with ID: " + seatId, exception.getMessage());
        verify(seatRepository, times(1)).findById(seatId);
    }

    @Test
    public void testSaveSeat() {
        // Datos de prueba
        Seat seat = new Seat();

        // Configuración del mock
        when(seatRepository.save(seat)).thenReturn(seat);

        // Ejecución del método a probar
        Seat result = seatService.saveSeat(seat);

        // Verificaciones
        assertNotNull(result);
        verify(seatRepository, times(1)).save(seat);
    }

    @Test
    public void testDeleteSeat_Found() {
        // Datos de prueba
        SeatId seatId = new SeatId(1, 1, 'A', 1);

        // Configuración del mock
        when(seatRepository.existsById(seatId)).thenReturn(true);

        // Ejecución del método a probar
        seatService.deleteSeat(seatId);

        // Verificaciones
        verify(seatRepository, times(1)).existsById(seatId);
        verify(seatRepository, times(1)).deleteById(seatId);
    }

    @Test
    public void testDeleteSeat_NotFound() {
        // Datos de prueba
        SeatId seatId = new SeatId(1, 1, 'A', 1);

        // Configuración del mock
        when(seatRepository.existsById(seatId)).thenReturn(false);

        // Ejecución y verificación de la excepción
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            seatService.deleteSeat(seatId);
        });

        assertEquals("Seat not found with ID: " + seatId, exception.getMessage());
        verify(seatRepository, times(1)).existsById(seatId);
        verify(seatRepository, never()).deleteById(any(SeatId.class));
    }
}
