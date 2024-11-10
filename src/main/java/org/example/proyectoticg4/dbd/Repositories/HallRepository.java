package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.HallId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HallRepository extends JpaRepository<Hall, HallId> {
}
