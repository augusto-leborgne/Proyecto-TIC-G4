package org.example.proyectoticg4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "seats")
public class Seat {

    @EmbeddedId
    @NotNull(message = "Seat ID cannot be null")
    @Valid
    private SeatId seatId;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "hall_number", referencedColumnName = "hNumber", insertable = false, updatable = false),
            @JoinColumn(name = "cinema_number", referencedColumnName = "cinemaNumber", insertable = false, updatable = false)
    })
    @NotNull(message = "Hall cannot be null")
    @Valid
    private Hall hall;


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
