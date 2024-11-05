package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.user.userId = :userId and t.reservation.reservationId = :reservationId")
    List<Ticket> findByUser_UserIdAndReservation_ReservationId(String userId, Long reservationId);

    @Query("SELECT t FROM Ticket t WHERE t.user.userId = :userId")
    List<Ticket> findByUser_UserId(String userId);
}
