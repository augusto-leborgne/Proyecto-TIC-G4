package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testSaveAndFindById() {
        // Crear una instancia de Movie
        Movie movie = new Movie();
        movie.setMovieId("M1");
        movie.setTitle("Inception");
        movie.setImage(new byte[]{1, 2, 3});

        // Guardar la película en la base de datos
        Movie savedMovie = movieRepository.save(movie);

        // Recuperar la película por su ID
        Movie foundMovie = movieRepository.findById("M1").orElse(null);

        // Verificaciones
        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getTitle()).isEqualTo("Inception");
        assertThat(foundMovie.getImage()).isEqualTo(new byte[]{1, 2, 3});
    }

    @Test
    public void testFindAllImages() {
        // Crear varias instancias de Movie
        Movie movie1 = new Movie();
        movie1.setMovieId("M1");
        movie1.setTitle("Inception");
        movie1.setImage(new byte[]{1, 2, 3});

        Movie movie2 = new Movie();
        movie2.setMovieId("M2");
        movie2.setTitle("Interstellar");
        movie2.setImage(new byte[]{4, 5, 6});

        // Guardar las películas en la base de datos
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // Recuperar todas las imágenes
        List<byte[]> images = movieRepository.findAllImages();

        // Verificaciones
        assertThat(images).hasSize(2);
        assertThat(images).containsExactlyInAnyOrder(movie1.getImage(), movie2.getImage());
    }

    @Test
    public void testDelete() {
        // Crear y guardar una película
        Movie movie = new Movie();
        movie.setMovieId("M1");
        movie.setTitle("Inception");
        movie.setImage(new byte[]{1, 2, 3});
        movieRepository.save(movie);

        // Eliminar la película
        movieRepository.deleteById("M1");

        // Verificar que la película ha sido eliminada
        boolean exists = movieRepository.existsById("M1");
        assertThat(exists).isFalse();
    }
}
