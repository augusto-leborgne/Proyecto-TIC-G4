package org.example.proyectoticg4.dbd.repositories;

import org.example.proyectoticg4.dbd.Entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Seat.SeatId> {
}
