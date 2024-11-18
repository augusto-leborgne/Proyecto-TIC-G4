package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.Reservation;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.ReservationRepository;
import org.example.proyectoticg4.repositories.ShowSeatAvailabilityRepository;
import org.example.proyectoticg4.repositories.TicketRepository;
import org.example.proyectoticg4.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    public ReservationServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReservations_ShouldReturnListOfReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(new Reservation(), new Reservation()));
        List<Reservation> reservations = reservationService.getAllReservations();
        assertEquals(2, reservations.size());
    }

    @Test
    void getReservationById_ShouldReturnReservation_WhenExists() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Reservation foundReservation = reservationService.getReservationById(1L);
        assertNotNull(foundReservation);
    }

    @Test
    void getReservationById_ShouldThrowException_WhenNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> reservationService.getReservationById(1L));
    }

    @Test
    void deleteReservation_ShouldDelete_WhenExists() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        reservationService.deleteReservation(reservation);

        verify(reservationRepository, times(1)).delete(reservation);
    }
}
