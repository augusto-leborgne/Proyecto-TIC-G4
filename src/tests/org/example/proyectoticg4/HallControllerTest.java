package org.example.proyectoticg4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.proyectoticg4.controllers.HallController;
import org.example.proyectoticg4.entities.Cinema;
import org.example.proyectoticg4.entities.Hall;
import org.example.proyectoticg4.entities.HallId;
import org.example.proyectoticg4.services.HallService;
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
public class HallControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HallService hallService;

    @InjectMocks
    private HallController hallController;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(hallController).build();
    }

    @Test
    public void testGetAllHalls() throws Exception {
        // Datos de prueba
        Hall hall1 = new Hall();
        hall1.setHallId(new HallId(1, 1));

        Hall hall2 = new Hall();
        hall2.setHallId(new HallId(2, 1));

        List<Hall> halls = Arrays.asList(hall1, hall2);

        // Configuración del mock
        when(hallService.getAllHalls()).thenReturn(halls);

        // Ejecución y verificación
        mockMvc.perform(get("/api/halls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id.hNumber", is(1)))
                .andExpect(jsonPath("$[0].id.ciNumber", is(1)))
                .andExpect(jsonPath("$[1].id.hNumber", is(2)))
                .andExpect(jsonPath("$[1].id.ciNumber", is(1)));

        verify(hallService, times(1)).getAllHalls();
    }

    @Test
    public void testGetHallById() throws Exception {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);
        Hall hall = new Hall();
        hall.setHallId(hallId);

        // Configuración del mock
        when(hallService.getHallById(eq(hallId))).thenReturn(hall);

        // Ejecución y verificación
        mockMvc.perform(get("/api/halls/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.hNumber", is(1)))
                .andExpect(jsonPath("$.id.ciNumber", is(1)));

        verify(hallService, times(1)).getHallById(eq(hallId));
    }

    @Test
    public void testCreateHall() throws Exception {
        // Datos de prueba
        Hall hall = new Hall();
        Cinema cinema = new Cinema();
        cinema.setCiNumber(1);
        hall.setCinema(cinema);

        Hall savedHall = new Hall();
        savedHall.setHallId(new HallId(1, 1));
        savedHall.setCinema(cinema);

        // Configuración del mock
        when(hallService.createHall(Mockito.any())).thenReturn(savedHall);

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(hall);

        // Ejecución y verificación
        mockMvc.perform(post("/api/halls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.hNumber", is(1)))
                .andExpect(jsonPath("$.id.ciNumber", is(1)));

        verify(hallService, times(1)).createHall(Mockito.any());
    }

    @Test
    public void testUpdateHall() throws Exception {
        // Datos de prueba
        HallId hallId = new HallId(1, 1);
        Hall updatedHall = new Hall();

        Hall returnedHall = new Hall();
        returnedHall.setHallId(hallId);

        // Configuración del mock
        when(hallService.updateHall(eq(hallId), Mockito.any())).thenReturn(returnedHall);

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(updatedHall);

        // Ejecución y verificación
        mockMvc.perform(put("/api/halls/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.hNumber", is(1)))
                .andExpect(jsonPath("$.id.ciNumber", is(1)));

        verify(hallService, times(1)).updateHall(eq(hallId), Mockito.any());
    }

    @Test
    public void testDeleteHall() throws Exception {
        // Ejecución y verificación
        mockMvc.perform(delete("/api/halls/1/1"))
                .andExpect(status().isOk());

        verify(hallService, times(1)).deleteHall(eq(new HallId(1, 1)));
    }
}
