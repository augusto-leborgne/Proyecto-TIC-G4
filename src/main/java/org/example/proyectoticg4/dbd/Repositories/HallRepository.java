package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.HallId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface HallRepository extends JpaRepository<Hall, HallId> {

    // Custom query to find Hall by HallId
    @Query("SELECT h FROM Hall h WHERE h.hallId = :id")
    Optional<Hall> findByHallId(HallId id);
}
