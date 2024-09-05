package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Hall.HallId> {
    Optional<Hall> findByHallNumberAndCinemaNumber(int hallNumber, int cinemaNumber);
    }
