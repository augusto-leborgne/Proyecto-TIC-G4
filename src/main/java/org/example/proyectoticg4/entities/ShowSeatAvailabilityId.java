package org.example.proyectoticg4.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

@Embeddable
public class ShowSeatAvailabilityId implements Serializable {

    @Positive(message = "Show code must be positive")
    private Integer showCode;

    @Positive(message = "Seat column must be positive")
    private Integer seatColumn;

    @Positive(message = "Seat row must be positive")
    private Integer seatRow;

    public ShowSeatAvailabilityId() {
    }

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

