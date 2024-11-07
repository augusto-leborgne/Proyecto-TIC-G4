package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.ShowSeatAvailability;
import org.example.proyectoticg4.dbd.Entities.ShowSeatAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowSeatAvailabilityRepository extends JpaRepository<ShowSeatAvailability, ShowSeatAvailabilityId> {
    @Query("SELECT s FROM ShowSeatAvailability s WHERE " +
            "(s.id.showCode, s.id.seatColumn, s.id.seatRow) IN :seatIds")
    List<ShowSeatAvailability> findByCompositeIds(@Param("id") List<ShowSeatAvailabilityId> seatIds);
}
