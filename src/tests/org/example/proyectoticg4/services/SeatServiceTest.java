package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.Seat;
import org.example.proyectoticg4.entities.SeatId;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    private Seat seat;
    private SeatId seatId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seatId = new SeatId(1, 1, 5, 10);
        seat = new Seat();
        seat.setseatId(seatId);
    }

    @Test
    void getAllSeats_ShouldReturnListOfSeats() {
        when(seatRepository.findAll()).thenReturn(List.of(seat, new Seat()));
        List<Seat> seats = seatService.getAllSeats();
        assertEquals(2, seats.size());
        verify(seatRepository, times(1)).findAll();
    }

    @Test
    void getSeatById_ShouldReturnSeat_WhenExists() {
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        Seat foundSeat = seatService.getSeatById(seatId);
        assertNotNull(foundSeat);
        assertEquals(seatId, foundSeat.getseatId());
        verify(seatRepository, times(1)).findById(seatId);
    }

    @Test
    void getSeatById_ShouldThrowException_WhenNotFound() {
        when(seatRepository.findById(seatId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> seatService.getSeatById(seatId));
        verify(seatRepository, times(1)).findById(seatId);
    }

    @Test
    void saveSeat_ShouldSaveSeat() {
        when(seatRepository.save(seat)).thenReturn(seat);
        Seat savedSeat = seatService.saveSeat(seat);
        assertNotNull(savedSeat);
        verify(seatRepository, times(1)).save(seat);
    }

    @Test
    void deleteSeat_ShouldDelete_WhenSeatExists() {
        when(seatRepository.existsById(seatId)).thenReturn(true);
        seatService.deleteSeat(seatId);
        verify(seatRepository, times(1)).deleteById(seatId);
    }

    @Test
    void deleteSeat_ShouldThrowException_WhenSeatNotFound() {
        when(seatRepository.existsById(seatId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> seatService.deleteSeat(seatId));
        verify(seatRepository, times(1)).existsById(seatId);
    }
}
