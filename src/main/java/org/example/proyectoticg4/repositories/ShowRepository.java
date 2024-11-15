package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Show;
import org.example.proyectoticg4.entities.ShowSeatAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {
    @Query("SELECT s FROM Show s WHERE s.hall.hallId.cinemaNumber = :cinemaNumber")
    List<Show> findShowsByCinemaNumber(int cinemaNumber);

    @Query("SELECT s FROM Show s WHERE s.hall.hallId.cinemaNumber = :cinemaNumber and s.movie.movieId = :movieId")
    List<Show> findByMovieIdAndCinemaNumber(String movieId, Integer cinemaNumber);

    @Query("SELECT s.showSeatAvailabilities FROM Show s WHERE s.hall.hallId.cinemaNumber = :cinemaNumber and s.movie.movieId = :movieId and s.showTime = :showTime")
    List<ShowSeatAvailability> findSeats(String movieId, Integer cinemaNumber, LocalDateTime showTime);
}
