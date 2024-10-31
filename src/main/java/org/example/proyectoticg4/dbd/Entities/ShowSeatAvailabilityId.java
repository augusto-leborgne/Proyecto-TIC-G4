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

    public ShowSeatAvailabilityId(Integer showCode, SeatId seatID) {
        this.showCode = showCode;
        this.hallNumber = seatID.getHallNumber();
        this.cinemaNumber = seatID.getcinemaNumber();
        this.seatColumn = seatID.getSeatColumn();
        this.seatRow = seatID.getSeatRow();
    }

    public Integer getShowCode() {
        return showCode;
    }

    public void setShowCode(Integer showCode) {
        this.showCode = showCode;
    }

    public Integer getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(Integer hallNumber) {
        this.hallNumber = hallNumber;
    }

    public Integer getCinemaNumber() {
        return cinemaNumber;
    }

    public void setCinemaNumber(Integer cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }

    public Integer getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(Integer seatColumn) {
        this.seatColumn = seatColumn;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }
}

