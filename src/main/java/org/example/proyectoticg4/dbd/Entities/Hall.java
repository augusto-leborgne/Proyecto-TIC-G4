package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;
import org.example.proyectoticg4.dbd.Entities.Cinema;
import org.example.proyectoticg4.dbd.Entities.Seat;

import java.util.Set;

@Entity
@Table(name = "halls")
public class Hall {

    @EmbeddedId
    private HallId id;

    @ManyToOne
    @MapsId("cinemaNumber")
    @JoinColumn(name = "cinema_number")
    private Cinema cinema;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Seat> seats;

    public HallId getId() {
        return id;
    }

    public void setId(HallId id) {
        this.id = id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    @Embeddable
    public static class HallId implements java.io.Serializable {
        private Integer hNumber;
        private Integer cinemaNumber;

        public void setHNumber(Integer integer) {
            this.hNumber = integer;
        }

        public Integer gethNumber() {
            return hNumber;
        }

        public void sethNumber(Integer hNumber) {
            this.hNumber = hNumber;
        }

        public Integer getCinemaNumber() {
            return cinemaNumber;
        }

        public void setCinemaNumber(Integer cinemaNumber) {
            this.cinemaNumber = cinemaNumber;
        }

        // Equals, hashcode
    }
}
