package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.userId = :userId")
    List<Reservation> findByUser_UserId(String userId);
}
