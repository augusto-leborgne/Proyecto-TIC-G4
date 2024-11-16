package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.ShowSeatAvailability;
import org.example.proyectoticg4.entities.ShowSeatAvailabilityId;
import org.example.proyectoticg4.repositories.ShowSeatAvailabilityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowSeatAvailabilityServiceTest {

    @Mock
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @InjectMocks
    private ShowSeatAvailabilityService showSeatAvailabilityService;

    @Test
    public void testFindSeatsByIds_Success() {
        // Test data
        ShowSeatAvailabilityId seatId1 = new ShowSeatAvailabilityId(1L, new SeatId(1, 1, 'A', 1));
        ShowSeatAvailabilityId seatId2 = new ShowSeatAvailabilityId(1L, new SeatId(1, 1, 'A', 2));
        List<ShowSeatAvailabilityId> seatIds = Arrays.asList(seatId1, seatId2);

        ShowSeatAvailability seat1 = new ShowSeatAvailability();
        seat1.setId(seatId1);

        ShowSeatAvailability seat2 = new ShowSeatAvailability();
        seat2.setId(seatId2);

        List<ShowSeatAvailability> seats = Arrays.asList(seat1, seat2);

        // Mock setup
        when(showSeatAvailabilityRepository.findAllById(seatIds)).thenReturn(seats);

        // Method execution
        List<ShowSeatAvailability> result = showSeatAvailabilityService.findSeatsByIds(seatIds);

        // Verification
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(seat1));
        assertTrue(result.contains(seat2));
        verify(showSeatAvailabilityRepository, times(1)).findAllById(seatIds);
    }

    @Test
    public void testFindSeatsByIds_EmptyList() {
        // Test data
        List<ShowSeatAvailabilityId> seatIds = Collections.emptyList();

        // Mock setup
        when(showSeatAvailabilityRepository.findAllById(seatIds)).thenReturn(Collections.emptyList());

        // Method execution
        List<ShowSeatAvailability> result = showSeatAvailabilityService.findSeatsByIds(seatIds);

        // Verification
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(showSeatAvailabilityRepository, times(1)).findAllById(seatIds);
    }

    @Test
    public void testFindSeatsByIds_NotAllFound() {
        // Test data
        ShowSeatAvailabilityId seatId1 = new ShowSeatAvailabilityId(1L, new SeatId(1, 1, 'A', 1));
        ShowSeatAvailabilityId seatId2 = new ShowSeatAvailabilityId(1L, new SeatId(1, 1, 'A', 2));
        ShowSeatAvailabilityId seatId3 = new ShowSeatAvailabilityId(1L, new SeatId(1, 1, 'A', 3)); // Non-existent seat
        List<ShowSeatAvailabilityId> seatIds = Arrays.asList(seatId1, seatId2, seatId3);

        ShowSeatAvailability seat1 = new ShowSeatAvailability();
        seat1.setId(seatId1);

        ShowSeatAvailability seat2 = new ShowSeatAvailability();
        seat2.setId(seatId2);

        List<ShowSeatAvailability> seats = Arrays.asList(seat1, seat2);

        // Mock setup
        when(showSeatAvailabilityRepository.findAllById(seatIds)).thenReturn(seats);

        // Method execution
        List<ShowSeatAvailability> result = showSeatAvailabilityService.findSeatsByIds(seatIds);

        // Verification
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(seat1));
        assertTrue(result.contains(seat2));
        verify(showSeatAvailabilityRepository, times(1)).findAllById(seatIds);
    }
}
