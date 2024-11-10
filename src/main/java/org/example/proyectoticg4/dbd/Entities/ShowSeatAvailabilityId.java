package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ShowSeatAvailabilityId implements Serializable {
    private Integer showCode;
    private Integer seatColumn;
    private Integer seatRow;

    public ShowSeatAvailabilityId() {
    }

    // Parameterized constructor
    public ShowSeatAvailabilityId(Integer showCode, SeatId seatID) {
        this.showCode = showCode;
        this.seatColumn = seatID.getSeatColumn();
        this.seatRow = seatID.getSeatRow();
    }

    public Integer getShowCode() {
        return showCode;
    }

    public void setShowCode(Integer showCode) {
        this.showCode = showCode;
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

