package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_code")
    private Long tCode;

    @ManyToOne
    @JoinColumn(name = "show_cod", referencedColumnName = "show_cod")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "hall_number", nullable = false)
    private Integer hallNumber;

    @Column(name = "cinema_number", nullable = false)
    private Integer cinemaNumber;

    @Column(name = "seat_column", nullable = false)
    private Integer seatColumn;

    @Column(name = "seat_row", nullable = false)
    private Integer seatRow;

    @Column(name = "showTime")
    private LocalDateTime showTime;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation;

    // Getters and Setters (including reservation)
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Long gettCode() {
        return tCode;
    }

    public void settCode(Long tCode) {
        this.tCode = tCode;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }
}
