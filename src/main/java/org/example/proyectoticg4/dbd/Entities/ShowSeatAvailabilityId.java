package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ShowSeatAvailabilityId implements Serializable {
    private Integer showCode;
    private Integer hallNumber;
    private Integer cinemaNumber;
    private Integer seatColumn;
    private Integer seatRow;

    // Default constructor, equals(), hashCode(), getters and setters
}

