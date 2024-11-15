package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
}
