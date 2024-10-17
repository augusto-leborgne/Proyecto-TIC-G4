package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_code")
    private Long tCode;

    @ManyToOne
    @JoinColumn(name = "show_cod", referencedColumnName = "show_cod")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "hall_number")
    private Integer hallNumber;

    @Column(name = "cinema_number")
    private Integer cinemaNumber;

    @Column(name = "seat_column", nullable = false)
    private Integer seatColumn;

    @Column(name = "seat_row", nullable = false)
    private Integer seatRow;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation;

    // Getters and Setters (including reservation)
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    // other getters and setters...
}
