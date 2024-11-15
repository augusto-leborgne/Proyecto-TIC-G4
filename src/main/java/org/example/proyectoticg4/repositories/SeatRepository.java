package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Seat;
import org.example.proyectoticg4.entities.SeatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, SeatId> {
}
