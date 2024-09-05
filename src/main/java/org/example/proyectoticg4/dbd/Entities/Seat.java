package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;
import org.example.proyectoticg4.dbd.Entities.Hall;

@Entity
@Table(name = "seats")
public class Seat {

    @EmbeddedId
    private SeatId id;

    @ManyToOne
    @MapsId("hallNumber")
    @JoinColumns({
            @JoinColumn(name = "hall_number", referencedColumnName = "h_number"),
            @JoinColumn(name = "hall_cinema_number", referencedColumnName = "cinema_number")
    })
    private Hall hall;

    @Column(name = "available")
    private Boolean available;

    // Getters and Setters

    @Embeddable
    public static class SeatId implements java.io.Serializable {
        private Integer hallNumber;
        private Integer cinemaNumber;
        private Integer seatRow;
        private Integer seatColumn;

        // Getters, Setters, equals, hashcode
    }
}
