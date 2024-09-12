package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;

@Entity
public class Seat {

    @EmbeddedId
    private SeatId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "hall_number", referencedColumnName = "hNumber", insertable = false, updatable = false),
            @JoinColumn(name = "hall_cinema_number", referencedColumnName = "cinemaNumber", insertable = false, updatable = false)
    })
    private Hall hall;

    @Enumerated(EnumType.STRING)
    private Availability available;

    public Seat() {
    }

    // Getters and setters

    public SeatId getId() {
        return id;
    }

    public void setId(SeatId id) {
        this.id = id;
    }

    public Availability getAvailable() {
        return available;
    }

    public void setAvailable(Availability available) {
        this.available = available;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public enum Availability {
        YES, NO
    }
}
