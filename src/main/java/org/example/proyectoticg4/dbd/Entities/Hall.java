package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "halls")
public class Hall {

    @EmbeddedId
    private HallId hallId;

    @JsonIgnore
    @ManyToOne
    @MapsId("cinemaNumber")
    @JoinColumn(name = "cinemaNumber", referencedColumnName = "ci_number", insertable = false, updatable = false)
    private Cinema cinema;

    @JsonIgnore
    @OneToMany(mappedBy = "hall")
    private List<Seat> seats;

    public HallId getHallId() {
        return hallId;
    }

    public void setHallId(HallId id) {
        this.hallId = id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

}
