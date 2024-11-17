package org.example.proyectoticg4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.proyectoticg4.controllers.ShowController;
import org.example.proyectoticg4.entities.*;
import org.example.proyectoticg4.services.HallService;
import org.example.proyectoticg4.services.MovieService;
import org.example.proyectoticg4.services.ShowService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ShowControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShowService showService;

    @Mock
    private MovieService movieService;

    @Mock
    private HallService hallService;

    @InjectMocks
    private ShowController showController;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(showController).build();
    }

    @Test
    public void testGetAllShows() throws Exception {
        // Datos de prueba
        Show show1 = new Show();
        show1.setShowCode(1);
        show1.setShowTime(LocalDateTime.now());

        Show show2 = new Show();
        show2.setShowCode(2);
        show2.setShowTime(LocalDateTime.now().plusHours(2));

        List<Show> shows = Arrays.asList(show1, show2);

        // Configuración del mock
        when(showService.getAllShows()).thenReturn(shows);

        // Ejecución y verificación
        mockMvc.perform(get("/api/shows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].showCode", is(1)))
                .andExpect(jsonPath("$[1].showCode", is(2)));

        verify(showService, times(1)).getAllShows();
    }

    @Test
    public void testGetMoviesByCinemaNumber() throws Exception {
        // Datos de prueba
        int cinemaNumber = 1;

        Cinema cinema = new Cinema();
        cinema.setCiNumber(cinemaNumber);

        Movie movie1 = new Movie();
        movie1.setMovieId("M1");

        Movie movie2 = new Movie();
        movie2.setMovieId("M2");

        Show show1 = new Show();
        show1.setShowCode(1);
        show1.setMovie(movie1);

        Show show2 = new Show();
        show2.setShowCode(2);
        show2.setMovie(movie2);

        List<Show> shows = Arrays.asList(show1, show2);

        // Configuración del mock
        when(showService.findShowsByCinemaNumber(cinemaNumber)).thenReturn(shows);

        // Ejecución y verificación
        mockMvc.perform(get("/api/shows/cinema/{cinemaNumber}", cinemaNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is("M1")))
                .andExpect(jsonPath("$[1].movieId", is("M2")));

        verify(showService, times(1)).findShowsByCinemaNumber(cinemaNumber);
    }

    @Test
    public void testGetShowtimesByMovieAndCinema() throws Exception {
        // Datos de prueba
        String movieId = "M1";
        int cinemaNumber = 1;

        Show show1 = new Show();
        show1.setShowTime(LocalDateTime.of(2023, 1, 1, 18, 0));

        Show show2 = new Show();
        show2.setShowTime(LocalDateTime.of(2023, 1, 1, 20, 0));

        List<Show> shows = Arrays.asList(show1, show2);

        // Configuración del mock
        when(showService.findShowsByMovieAndCinema(movieId, cinemaNumber)).thenReturn(shows);

        // Ejecución y verificación
        mockMvc.perform(get("/api/shows/showtimes")
                        .param("movieId", movieId)
                        .param("cinemaNumber", String.valueOf(cinemaNumber)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(showService, times(1)).findShowsByMovieAndCinema(movieId, cinemaNumber);
    }

    @Test
    public void testGetShowSeats() throws Exception {
        // Datos de prueba
        String movieId = "M1";
        int cinemaNumber = 1;
        LocalDateTime showTime = LocalDateTime.of(2023, 1, 1, 18, 0);

        ShowSeatAvailability seatAvailability1 = new ShowSeatAvailability();
        seatAvailability1.setAvailable(true);

        ShowSeatAvailability seatAvailability2 = new ShowSeatAvailability();
        seatAvailability2.setAvailable(false);

        List<ShowSeatAvailability> seatAvailabilities = Arrays.asList(seatAvailability1, seatAvailability2);

        // Configuración del mock
        when(showService.findSeats(movieId, cinemaNumber, showTime)).thenReturn(seatAvailabilities);

        // Ejecución y verificación
        mockMvc.perform(get("/api/shows/seats")
                        .param("movieId", movieId)
                        .param("cinemaNumber", String.valueOf(cinemaNumber))
                        .param("showTime", showTime.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(showService, times(1)).findSeats(movieId, cinemaNumber, showTime);
    }

    @Test
    public void testCreateShow() throws Exception {
        // Datos de prueba
        Movie movie = new Movie();
        movie.setMovieId("M1");

        Hall hall = new Hall();
        HallId hallId = new HallId(1, 1);
        hall.setHallId(hallId);

        Show show = new Show();
        show.setMovie(movie);
        show.setHall(hall);
        show.setShowTime(LocalDateTime.now());
        show.setPrice(100);

        Show savedShow = new Show();
        savedShow.setShowCode(1);
        savedShow.setMovie(movie);
        savedShow.setHall(hall);
        savedShow.setShowTime(show.getShowTime());
        savedShow.setPrice(show.getPrice());

        // Configuración del mock
        when(movieService.getMovieById("M1")).thenReturn(movie);
        when(hallService.getHallById(hallId)).thenReturn(hall);
        when(showService.createShowWithAvailableSeats(eq(movie), eq(hall), Mockito.any(LocalDateTime.class), eq(100))).thenReturn(savedShow);

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(show);

        // Ejecución y verificación
        mockMvc.perform(post("/api/shows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Show created with ID: 1"));

        verify(movieService, times(1)).getMovieById("M1");
        verify(hallService, times(1)).getHallById(hallId);
        verify(showService, times(1)).createShowWithAvailableSeats(eq(movie), eq(hall), Mockito.any(LocalDateTime.class), eq(100));
    }

    @Test
    public void testDeleteShow() throws Exception {
        // Datos de prueba
        int showId = 1;

        // Configuración del mock
        doNothing().when(showService).deleteShow(showId);

        // Ejecución y verificación
        mockMvc.perform(delete("/api/shows/{id}", showId))
                .andExpect(status().isOk());

        verify(showService, times(1)).deleteShow(showId);
    }

    @Test
    public void testDeleteAllShows() throws Exception {
        // Configuración del mock
        doNothing().when(showService).deleteAllShows();

        // Ejecución y verificación
        mockMvc.perform(delete("/api/shows/all"))
                .andExpect(status().isOk());

        verify(showService, times(1)).deleteAllShows();
    }
}
