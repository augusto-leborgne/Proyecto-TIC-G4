package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeatId implements Serializable {

    @Column(name = "hall_number")
    @Positive(message = "Hall number must be positive")
    private int hallNumber;

    @Column(name = "cinema_number")
    @Positive(message = "Cinema number must be positive")
    private int cinemaNumber;

    @Column(name = "seat_column")
    @Positive(message = "Seat column must be positive")
    private int seatColumn;

    @Column(name = "seat_row")
    @Positive(message = "Seat row must be positive")
    private int seatRow;

    public SeatId() {
    }

    public SeatId(int hallNumber, int cinemaNumber, int seatColumn, int seatRow) {
        this.hallNumber = hallNumber;
        this.cinemaNumber = cinemaNumber;
        this.seatColumn = seatColumn;
        this.seatRow = seatRow;
    }


    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    public int getcinemaNumber() {
        return cinemaNumber;
    }

    public void setcinemaNumber(int cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatId seatId = (SeatId) o;
        return hallNumber == seatId.hallNumber &&
                cinemaNumber == seatId.cinemaNumber &&
                seatColumn == seatId.seatColumn &&
                seatRow == seatId.seatRow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallNumber, cinemaNumber, seatColumn, seatRow);
    }
}
