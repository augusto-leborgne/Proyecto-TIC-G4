package org.example.proyectoticg4;

Optimimport com.fasterxml.jackson.databind.ObjectMapper;
import org.example.proyectoticg4.controllers.CinemaController;
import org.example.proyectoticg4.entities.Cinema;
import org.example.proyectoticg4.services.CinemaService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CinemaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CinemaService cinemaService;

    @InjectMocks
    private CinemaController cinemaController;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllCinemas() throws Exception {
        // Datos de prueba
        Cinema cinema1 = new Cinema();
        cinema1.setCiNumber(1);
        cinema1.setNeighborhood("Centro");

        Cinema cinema2 = new Cinema();
        cinema2.setCiNumber(2);
        cinema2.setNeighborhood("Norte");

        List<Cinema> cinemas = Arrays.asList(cinema1, cinema2);

        // Configuración del mock
        when(cinemaService.getAllCinemas()).thenReturn(cinemas);

        // Configuración de MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(cinemaController).build();

        // Ejecución y verificación
        mockMvc.perform(get("/api/cinemas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ciNumber", is(1)))
                .andExpect(jsonPath("$[0].neighborhood", is("Centro")))
                .andExpect(jsonPath("$[1].ciNumber", is(2)))
                .andExpect(jsonPath("$[1].neighborhood", is("Norte")));

        verify(cinemaService, times(1)).getAllCinemas();
    }

    @Test
    public void testGetCinemaById() throws Exception {
        // Datos de prueba
        Cinema cinema = new Cinema();
        cinema.setCiNumber(1);
        cinema.setNeighborhood("Centro");

        // Configuración del mock
        when(cinemaService.getCinemaById(1)).thenReturn(cinema);

        // Configuración de MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(cinemaController).build();

        // Ejecución y verificación
        mockMvc.perform(get("/api/cinemas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ciNumber", is(1)))
                .andExpect(jsonPath("$.neighborhood", is("Centro")));

        verify(cinemaService, times(1)).getCinemaById(1);
    }

    @Test
    public void testCreateCinema() throws Exception {
        // Datos de prueba
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");

        Cinema savedCinema = new Cinema();
        savedCinema.setCiNumber(1);
        savedCinema.setNeighborhood("Centro");

        // Configuración del mock
        when(cinemaService.createCinema(Mockito.any())).thenReturn(savedCinema);

        // Configuración de MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(cinemaController).build();

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(cinema);

        // Ejecución y verificación
        mockMvc.perform(post("/api/cinemas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ciNumber", is(1)))
                .andExpect(jsonPath("$.neighborhood", is("Centro")));

        verify(cinemaService, times(1)).createCinema(Mockito.any());
    }

    @Test
    public void testUpdateCinema() throws Exception {
        // Datos de prueba
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Norte");

        Cinema updatedCinema = new Cinema();
        updatedCinema.setCiNumber(1);
        updatedCinema.setNeighborhood("Norte");

        // Configuración del mock
        when(cinemaService.updateCinema(eq(1), Mockito.any())).thenReturn(updatedCinema);

        // Configuración de MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(cinemaController).build();

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(cinema);

        // Ejecución y verificación
        mockMvc.perform(put("/api/cinemas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ciNumber", is(1)))
                .andExpect(jsonPath("$.neighborhood", is("Norte")));

        verify(cinemaService, times(1)).updateCinema(eq(1), Mockito.any());
    }

    @Test
    public void testDeleteCinema() throws Exception {
        // Configuración de MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(cinemaController).build();

        // Ejecución y verificación
        mockMvc.perform(delete("/api/cinemas/1"))
                .andExpect(status().isOk());

        verify(cinemaService, times(1)).deleteCinema(1);
    }
}
