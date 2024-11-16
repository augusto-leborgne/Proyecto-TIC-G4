package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Cinema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CinemaRepositoryTest {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Test
    public void testSaveAndFindById() {
        // Crear una instancia de Cinema
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");

        // Guardar el cine en la base de datos
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Recuperar el cine por su ID
        Cinema foundCinema = cinemaRepository.findById(savedCinema.getCiNumber()).orElse(null);

        // Verificaciones
        assertThat(foundCinema).isNotNull();
        assertThat(foundCinema.getNeighborhood()).isEqualTo("Centro");
    }

    @Test
    public void testFindAll() {
        // Crear varias instancias de Cinema
        Cinema cinema1 = new Cinema();
        cinema1.setNeighborhood("Centro");

        Cinema cinema2 = new Cinema();
        cinema2.setNeighborhood("Norte");

        // Guardar los cines en la base de datos
        cinemaRepository.save(cinema1);
        cinemaRepository.save(cinema2);

        // Recuperar todos los cines
        Iterable<Cinema> cinemas = cinemaRepository.findAll();

        // Verificaciones
        assertThat(cinemas).hasSize(2);
    }

    @Test
    public void testDelete() {
        // Crear y guardar un cine
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Eliminar el cine
        cinemaRepository.delete(savedCinema);

        // Verificar que el cine ha sido eliminado
        boolean exists = cinemaRepository.existsById(savedCinema.getCiNumber());
        assertThat(exists).isFalse();
    }
}
