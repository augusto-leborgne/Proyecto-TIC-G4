package org.example.proyectoticg4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.proyectoticg4.controllers.SeatController;
import org.example.proyectoticg4.entities.Seat;
import org.example.proyectoticg4.entities.SeatId;
import org.example.proyectoticg4.services.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class SeatControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private SeatController seatController;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(seatController).build();
    }

    @Test
    public void testGetAllSeats() throws Exception {
        // Datos de prueba
        Seat seat1 = new Seat();
        seat1.setseatId(new SeatId(1, 1, 1, 1));

        Seat seat2 = new Seat();
        seat2.setseatId(new SeatId(1, 1, 1, 2));

        List<Seat> seats = Arrays.asList(seat1, seat2);

        // Configuración del mock
        when(seatService.getAllSeats()).thenReturn(seats);

        // Ejecución y verificación
        mockMvc.perform(get("/api/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].seatId.hNumber", is(1)))
                .andExpect(jsonPath("$[0].seatId.ciNumber", is(1)))
                .andExpect(jsonPath("$[0].seatId.seatColumn", is(1)))
                .andExpect(jsonPath("$[0].seatId.seatRow", is(1)))
                .andExpect(jsonPath("$[1].seatId.seatRow", is(2)));

        verify(seatService, times(1)).getAllSeats();
    }

    @Test
    public void testGetSeatById() throws Exception {
        // Datos de prueba
        SeatId seatId = new SeatId(1, 1, 1, 1);
        Seat seat = new Seat();
        seat.setseatId(seatId);

        // Configuración del mock
        when(seatService.getSeatById(eq(seatId))).thenReturn(seat);

        // Ejecución y verificación
        mockMvc.perform(get("/api/seats/1/1/A/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatId.hNumber", is(1)))
                .andExpect(jsonPath("$.seatId.ciNumber", is(1)))
                .andExpect(jsonPath("$.seatId.seatColumn", is(1)))
                .andExpect(jsonPath("$.seatId.seatRow", is(1)));

        verify(seatService, times(1)).getSeatById(eq(seatId));
    }

    @Test
    public void testCreateSeat() throws Exception {
        // Datos de prueba
        Seat seat = new Seat();
        SeatId seatId = new SeatId(1, 1, 1, 1);
        seat.setseatId(seatId);

        // Configuración del mock
        when(seatService.saveSeat(Mockito.any(Seat.class))).thenReturn(seat);

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(seat);

        // Ejecución y verificación
        mockMvc.perform(post("/api/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatId.hNumber", is(1)))
                .andExpect(jsonPath("$.seatId.ciNumber", is(1)))
                .andExpect(jsonPath("$.seatId.seatColumn", is(1)))
                .andExpect(jsonPath("$.seatId.seatRow", is(1)));

        verify(seatService, times(1)).saveSeat(Mockito.any(Seat.class));
    }

    @Test
    public void testDeleteSeat() throws Exception {
        // Datos de prueba
        SeatId seatId = new SeatId(1, 1, 1, 1);

        // Configuración del mock
        doNothing().when(seatService).deleteSeat(eq(seatId));

        // Ejecución y verificación
        mockMvc.perform(delete("/api/seats/1/1/A/1"))
                .andExpect(status().isOk());

        verify(seatService, times(1)).deleteSeat(eq(seatId));
    }
}
