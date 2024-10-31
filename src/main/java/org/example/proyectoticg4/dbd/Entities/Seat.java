package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @EmbeddedId
    private SeatId seatId;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "hall_number", referencedColumnName = "hNumber", insertable = false, updatable = false),
            @JoinColumn(name = "cinema_number", referencedColumnName = "cinemaNumber", insertable = false, updatable = false)
    })
    private Hall hall;

    public Seat() {
    }

    // Getters and setters

    public SeatId getseatId() {
        return seatId;
    }

    public void setseatId(SeatId seatId) {
        this.seatId = seatId;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
