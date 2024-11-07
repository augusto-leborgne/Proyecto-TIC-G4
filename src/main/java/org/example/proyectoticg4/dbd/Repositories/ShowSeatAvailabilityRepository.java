package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.ShowSeatAvailability;
import org.example.proyectoticg4.dbd.Entities.ShowSeatAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSeatAvailabilityRepository extends JpaRepository<ShowSeatAvailability, ShowSeatAvailabilityId> {
}
