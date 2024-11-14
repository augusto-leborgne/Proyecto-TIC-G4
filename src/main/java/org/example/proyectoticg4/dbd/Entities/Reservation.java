package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "show_cod", referencedColumnName = "show_cod")
    @NotNull(message = "Show cannot be null")
    @Valid
    private Show show;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = "User cannot be null")
    @Valid
    private User user;

    @Column(name = "reservation_time")
    @NotNull(message = "Reservation time cannot be null")
    private LocalDateTime reservationTime;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Total price cannot be null")
    @PositiveOrZero(message = "Total price cannot be negative")
    private Integer total;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @Valid
    private List<Ticket> tickets;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
