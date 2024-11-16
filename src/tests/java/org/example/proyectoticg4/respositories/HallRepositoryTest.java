package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Cinema;
import org.example.proyectoticg4.entities.Hall;
import org.example.proyectoticg4.entities.HallId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class HallRepositoryTest {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Test
    public void testSaveAndFindById() {
        // Crear una instancia de Cinema
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear una instancia de Hall
        HallId hallId = new HallId(savedCinema.getCiNumber(), 1);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hall.setSeats(100);

        // Guardar el hall en la base de datos
        Hall savedHall = hallRepository.save(hall);

        // Recuperar el hall por su ID
        Hall foundHall = hallRepository.findById(hallId).orElse(null);

        // Verificaciones
        assertThat(foundHall).isNotNull();
        assertThat(foundHall.getId()).isEqualTo(hallId);
        assertThat(foundHall.getSeats()).isEqualTo(100);
        assertThat(foundHall.getCinema().getCiNumber()).isEqualTo(savedCinema.getCiNumber());
    }

    @Test
    public void testFindAll() {
        // Crear una instancia de Cinema
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear y guardar varias instancias de Hall
        HallId hallId1 = new HallId(savedCinema.getCiNumber(), 1);
        Hall hall1 = new Hall();
        hall1.setId(hallId1);
        hall1.setCinema(savedCinema);
        hall1.setSeats(100);

        HallId hallId2 = new HallId(savedCinema.getCiNumber(), 2);
        Hall hall2 = new Hall();
        hall2.setId(hallId2);
        hall2.setCinema(savedCinema);
        hall2.setSeats(150);

        hallRepository.save(hall1);
        hallRepository.save(hall2);

        // Recuperar todos los halls
        Iterable<Hall> halls = hallRepository.findAll();

        // Verificaciones
        assertThat(halls).hasSize(2);
    }

    @Test
    public void testDelete() {
        // Crear una instancia de Cinema
        Cinema cinema = new Cinema();
        cinema.setNeighborhood("Centro");
        Cinema savedCinema = cinemaRepository.save(cinema);

        // Crear y guardar un Hall
        HallId hallId = new HallId(savedCinema.getCiNumber(), 1);
        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setCinema(savedCinema);
        hall.setSeats(100);

        Hall savedHall = hallRepository.save(hall);

        // Eliminar el Hall
        hallRepository.delete(savedHall);

        // Verificar que el Hall ha sido eliminado
        boolean exists = hallRepository.existsById(hallId);
        assertThat(exists).isFalse();
    }
}
