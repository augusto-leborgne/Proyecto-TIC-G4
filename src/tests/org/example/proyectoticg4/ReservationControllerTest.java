package org.example.proyectoticg4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.proyectoticg4.controllers.ReservationController;
import org.example.proyectoticg4.entities.Reservation;
import org.example.proyectoticg4.entities.ShowSeatAvailability;
import org.example.proyectoticg4.entities.ShowSeatAvailabilityId;
import org.example.proyectoticg4.entities.User;
import org.example.proyectoticg4.services.ReservationService;
import org.example.proyectoticg4.services.ShowSeatAvailabilityService;
import org.example.proyectoticg4.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @Mock
    private UserService userService;

    @Mock
    private ShowSeatAvailabilityService showSeatAvailabilityService;

    @InjectMocks
    private ReservationController reservationController;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void testGetAllReservations() throws Exception {
        // Datos de prueba
        Reservation reservation1 = new Reservation();
        reservation1.setReservationId(1L);
        reservation1.setTotal(100);

        Reservation reservation2 = new Reservation();
        reservation2.setReservationId(2L);
        reservation2.setTotal(150);

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        // Configuración del mock
        when(reservationService.getAllReservations()).thenReturn(reservations);

        // Ejecución y verificación
        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].total", is(100)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].total", is(150)));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    public void testCreateReservation() throws Exception {
        // Datos de prueba
        String userId = "user1";
        User user = new User();
        user.setUserId(userId);

        ShowSeatAvailabilityId seatId1 = new ShowSeatAvailabilityId();
        seatId1.setShowCode(1);

        ShowSeatAvailabilityId seatId2 = new ShowSeatAvailabilityId();
        seatId2.setShowCode(1);

        List<ShowSeatAvailabilityId> selectedSeatsId = Arrays.asList(seatId1, seatId2);

        ShowSeatAvailability seatAvailability1 = new ShowSeatAvailability();
        seatAvailability1.setId(seatId1);

        ShowSeatAvailability seatAvailability2 = new ShowSeatAvailability();
        seatAvailability2.setId(seatId2);

        List<ShowSeatAvailability> selectedSeats = Arrays.asList(seatAvailability1, seatAvailability2);

        Reservation createdReservation = new Reservation();
        createdReservation.setReservationId(1L);
        createdReservation.setUser(user);
        createdReservation.setTotal(200);

        // Configuración del mock
        when(userService.getUserById(userId)).thenReturn(user);
        when(showSeatAvailabilityService.findSeatsByIds(selectedSeatsId)).thenReturn(selectedSeats);
        when(reservationService.createReservationWithTickets(eq(user), eq(selectedSeats))).thenReturn(createdReservation);

        // Conversión del objeto a JSON
        String seatsJson = mapper.writeValueAsString(selectedSeatsId);

        // Ejecución y verificación
        mockMvc.perform(post("/api/reservations")
                        .param("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seatsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.total", is(200.0)));

        verify(userService, times(1)).getUserById(userId);
        verify(showSeatAvailabilityService, times(1)).findSeatsByIds(selectedSeatsId);
        verify(reservationService, times(1)).createReservationWithTickets(eq(user), eq(selectedSeats));
    }

    @Test
    public void testDeleteReservation() throws Exception {
        // Datos de prueba
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        // Configuración del mock
        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);
        doNothing().when(reservationService).deleteReservation(reservation);

        // Ejecución y verificación
        mockMvc.perform(delete("/api/reservations")
                        .param("reservationId", reservationId.toString()))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).getReservationById(reservationId);
        verify(reservationService, times(1)).deleteReservation(reservation);
    }
}
