package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.*;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.ReservationRepository;
import org.example.proyectoticg4.repositories.ShowSeatAvailabilityRepository;
import org.example.proyectoticg4.repositories.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void testCreateReservationWithTickets_Success() {
        // Datos de prueba
        User user = new User();
        user.setId(1L);

        Show show = new Show();
        show.setShowCode(1L);
        show.setPrice(10.0);

        ShowSeatAvailability seat1 = mock(ShowSeatAvailability.class);
        ShowSeatAvailability seat2 = mock(ShowSeatAvailability.class);

        when(seat1.getAvailable()).thenReturn(true);
        when(seat2.getAvailable()).thenReturn(true);

        when(seat1.getShow()).thenReturn(show);
        when(seat2.getShow()).thenReturn(show);

        ShowSeatAvailabilityId seatId1 = new ShowSeatAvailabilityId(1L, new SeatId(1, 1, 'A', 1));
        ShowSeatAvailabilityId seatId2 = new ShowSeatAvailabilityId(1L, new SeatId(1, 1, 'A', 2));

        when(seat1.getId()).thenReturn(seatId1);
        when(seat2.getId()).thenReturn(seatId2);

        List<ShowSeatAvailability> selectedSeats = Arrays.asList(seat1, seat2);

        Reservation savedReservation = new Reservation();
        savedReservation.setId(1L);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        // Ejecución del método a probar
        Reservation result = reservationService.createReservationWithTickets(user, selectedSeats);

        // Verificaciones
        assertNotNull(result);
        assertEquals(savedReservation.getId(), result.getId())
