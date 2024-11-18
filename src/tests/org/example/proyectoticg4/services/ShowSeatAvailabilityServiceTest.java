package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.SeatId;
import org.example.proyectoticg4.entities.ShowSeatAvailability;
import org.example.proyectoticg4.entities.ShowSeatAvailabilityId;
import org.example.proyectoticg4.repositories.ShowSeatAvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowSeatAvailabilityServiceTest {

    @Mock
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @InjectMocks
    private ShowSeatAvailabilityService showSeatAvailabilityService;

    private ShowSeatAvailabilityId seatAvailabilityId1;
    private ShowSeatAvailabilityId seatAvailabilityId2;
    private ShowSeatAvailability showSeatAvailability1;
    private ShowSeatAvailability showSeatAvailability2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize SeatId objects
        SeatId seatId1 = new SeatId(1, 2, 5, 10);
        SeatId seatId2 = new SeatId(1, 2, 6, 11);

        // Initialize ShowSeatAvailabilityId objects
        seatAvailabilityId1 = new ShowSeatAvailabilityId(101, seatId1);
        seatAvailabilityId2 = new ShowSeatAvailabilityId(101, seatId2);

        // Initialize ShowSeatAvailability entities
        showSeatAvailability1 = new ShowSeatAvailability();
        showSeatAvailability1.setId(seatAvailabilityId1);
        showSeatAvailability1.setAvailable(true);

        showSeatAvailability2 = new ShowSeatAvailability();
        showSeatAvailability2.setId(seatAvailabilityId2);
        showSeatAvailability2.setAvailable(false);
    }

    @Test
    void findSeatsByIds_ShouldReturnSeats_WhenSeatsExist() {
        List<ShowSeatAvailabilityId> ids = List.of(seatAvailabilityId1, seatAvailabilityId2);
        when(showSeatAvailabilityRepository.findAllById(ids))
                .thenReturn(List.of(showSeatAvailability1, showSeatAvailability2));

        List<ShowSeatAvailability> foundSeats = showSeatAvailabilityService.findSeatsByIds(ids);

        assertEquals(2, foundSeats.size());
        assertTrue(foundSeats.contains(showSeatAvailability1));
        assertTrue(foundSeats.contains(showSeatAvailability2));
        verify(showSeatAvailabilityRepository, times(1)).findAllById(ids);
    }

    @Test
    void findSeatsByIds_ShouldReturnEmptyList_WhenNoSeatsExist() {
        List<ShowSeatAvailabilityId> ids = List.of(seatAvailabilityId1, seatAvailabilityId2);
        when(showSeatAvailabilityRepository.findAllById(ids)).thenReturn(List.of());

        List<ShowSeatAvailability> foundSeats = showSeatAvailabilityService.findSeatsByIds(ids);

        assertTrue(foundSeats.isEmpty());
        verify(showSeatAvailabilityRepository, times(1)).findAllById(ids);
    }

    @Test
    void findSeatsByIds_ShouldHandlePartialResults_WhenSomeSeatsExist() {
        List<ShowSeatAvailabilityId> ids = List.of(seatAvailabilityId1, seatAvailabilityId2);
        when(showSeatAvailabilityRepository.findAllById(ids))
                .thenReturn(List.of(showSeatAvailability1));

        List<ShowSeatAvailability> foundSeats = showSeatAvailabilityService.findSeatsByIds(ids);

        assertEquals(1, foundSeats.size());
        assertTrue(foundSeats.contains(showSeatAvailability1));
        assertFalse(foundSeats.contains(showSeatAvailability2));
        verify(showSeatAvailabilityRepository, times(1)).findAllById(ids);
    }
}
