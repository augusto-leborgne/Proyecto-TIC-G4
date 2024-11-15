package org.example.proyectoticg4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_code")
    private Long tCode;

    @Column(name = "movie_name")
    @NotBlank(message = "Movie name cannot be blank")
    private String movieName;

    @Column(name = "hall_number")
    @NotNull(message = "Hall number cannot be null")
    private Integer hallNumber;

    @Column(name = "cinema_number")
    @NotNull(message = "Cinema number cannot be null")
    private Integer cinemaNumber;

    @Column(name = "seat_column")
    @NotNull(message = "Seat column cannot be null")
    private Integer seatColumn;

    @Column(name = "seat_row")
    @NotNull(message = "Seat row cannot be null")
    private Integer seatRow;

    @Column(name = "showTime")
    @NotNull(message = "Show time cannot be null")
    private LocalDateTime showTime;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    @NotNull(message = "Reservation cannot be null")
    private Reservation reservation;


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

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }
}
