package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Hall;
import org.example.proyectoticg4.entities.HallId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HallRepository extends JpaRepository<Hall, HallId> {
}
