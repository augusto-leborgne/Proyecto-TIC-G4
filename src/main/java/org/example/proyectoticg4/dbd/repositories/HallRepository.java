package org.example.proyectoticg4.dbd.repositories;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Hall.HallId> {
}