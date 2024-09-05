package org.example.proyectoticg4.dbd.repositories;

import org.example.proyectoticg4.dbd.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
