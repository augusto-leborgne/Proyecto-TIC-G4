package org.example.proyectoticg4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.proyectoticg4.controllers.MovieController;
import org.example.proyectoticg4.entities.Movie;
import org.example.proyectoticg4.services.MovieService;
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
public class MovieControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void testGetAllMovies() throws Exception {
        // Datos de prueba
        Movie movie1 = new Movie();
        movie1.setMovieId("M1");

        Movie movie2 = new Movie();
        movie2.setMovieId("M2");

        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Configuración del mock
        when(movieService.getAllMovies()).thenReturn(movies);

        // Ejecución y verificación
        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is("M1")))
                .andExpect(jsonPath("$[1].movieId", is("M2")));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    public void testGetMovieById() throws Exception {
        // Datos de prueba
        Movie movie = new Movie();
        movie.setMovieId("M1");

        // Configuración del mock
        when(movieService.getMovieById("M1")).thenReturn(movie);

        // Ejecución y verificación
        mockMvc.perform(get("/api/movies/M1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is("M1")));

        verify(movieService, times(1)).getMovieById("M1");
    }

    @Test
    public void testCreateMovie() throws Exception {
        // Datos de prueba
        Movie movie = new Movie();
        movie.setMovieId("M3");
        movie.setImage(new byte[]{1, 2, 3});

        // Configuración del mock
        doNothing().when(movieService).saveMovie(Mockito.any(Movie.class));

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(movie);

        // Ejecución y verificación
        mockMvc.perform(post("/api/movies/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie uploaded successfully with image"));

        verify(movieService, times(1)).saveMovie(Mockito.any(Movie.class));
    }

    @Test
    public void testUpdateMovie() throws Exception {
        Movie updatedMovie = new Movie();
        updatedMovie.setMovieId("M1");

        // Configuración del mock
        when(movieService.updateMovie(eq("M1"), Mockito.any(Movie.class))).thenReturn(updatedMovie);

        // Conversión del objeto a JSON
        String json = mapper.writeValueAsString(updatedMovie);

        // Ejecución y verificación
        mockMvc.perform(put("/api/movies/M1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is("M1")));

        verify(movieService, times(1)).updateMovie(eq("M1"), Mockito.any(Movie.class));
    }

    @Test
    public void testDeleteMovie() throws Exception {
        // Configuración del mock
        doNothing().when(movieService).deleteMovie("M1");

        // Ejecución y verificación
        mockMvc.perform(delete("/api/movies/M1"))
                .andExpect(status().isOk());

        verify(movieService, times(1)).deleteMovie("M1");
    }

    @Test
    public void testGetAllImages() throws Exception {
        // Datos de prueba
        byte[] image1 = new byte[]{1, 2, 3};
        byte[] image2 = new byte[]{4, 5, 6};

        List<byte[]> images = Arrays.asList(image1, image2);

        // Configuración del mock
        when(movieService.getAllImages()).thenReturn(images);

        // Ejecución y verificación
        mockMvc.perform(get("/api/movies/image"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(movieService, times(1)).getAllImages();
    }
}
