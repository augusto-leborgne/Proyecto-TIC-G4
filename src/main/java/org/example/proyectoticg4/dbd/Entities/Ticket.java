package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;

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
    private SeatId seatId;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SeatId getSeatId() {
        return seatId;
    }

    public void setSeatId(SeatId seatId) {
        this.seatId = seatId;
    }

}
