package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;
import org.example.proyectoticg4.dbd.Entities.Seat;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_code")
    private Long code;

    @Column(name = "price")
    private Double price;

    @Embedded
    private Seat.SeatId seatId;

    // Getters and Setters
}
