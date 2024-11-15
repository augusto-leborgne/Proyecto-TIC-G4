package org.example.proyectoticg4.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HallId implements Serializable {

    @Positive(message = "Hall number must be positive")
    private int hNumber;

    @Positive(message = "Cinema number must be positive")
    private int cinemaNumber;

    public HallId() {
    }

    public HallId(int hNumber, int cinemaNumber) {
        this.hNumber = hNumber;
        this.cinemaNumber = cinemaNumber;
    }

    public int gethNumber() {
        return hNumber;
    }

    public void sethNumber(int hNumber) {
        this.hNumber = hNumber;
    }

    public int getCinemaNumber() {
        return cinemaNumber;
    }

    public void setCinemaNumber(int cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallId hallId = (HallId) o;
        return hNumber == hallId.hNumber && cinemaNumber == hallId.cinemaNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hNumber, cinemaNumber);
    }
}
