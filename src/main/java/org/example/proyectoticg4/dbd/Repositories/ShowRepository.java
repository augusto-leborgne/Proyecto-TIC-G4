package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {
    @Query("SELECT s FROM Show s WHERE s.hall.hallId.cinemaNumber = :cinemaNumber")
    List<Show> findShowsByCinemaNumber(int cinemaNumber);

    @Query("SELECT s FROM Show s WHERE s.hall.hallId.cinemaNumber = :cinemaNumber and s.movie.movieId = :movieId")
    List<Show> findByMovieIdAndCinemaNumber(String movieId, Integer cinemaNumber);
}
