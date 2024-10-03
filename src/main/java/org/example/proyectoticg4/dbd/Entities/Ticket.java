package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_code")
    private Integer ticketCode; // Unique ticket code

    @ManyToOne
    @JoinColumn(name = "show_cod", nullable = false)
    private Show show; // Reference to the show

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Reference to the user who purchased the ticket

    @Column(name = "price", nullable = false)
    private Double price; // Price of the ticket

    @Column(name = "hall_number", nullable = false)
    private Integer hallNumber; // Reference to the hall's seat number

    @Column(name = "cinema_number", nullable = false)
    private Integer cinemaNumber; // Reference to the cinema's number for the seat

    @Column(name = "seat_column", nullable = false)
    private Integer seatColumn; // Reference to the seat column

    @Column(name = "seat_row", nullable = false)
    private Integer seatRow; // Reference to the seat row

    // Getters and Setters
    public Integer getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(Integer ticketCode) {
        this.ticketCode = ticketCode;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
