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

    // Getters and Setters

    @Embeddable
    public static class HallId implements java.io.Serializable {
        private Integer hNumber;
        private Integer cinemaNumber;

        public void setHNumber(Integer integer) {
            this.hNumber = integer;
        }

        // Getters, Setters, equals, hashcode
    }
}
