package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "show_seat_availability")
public class ShowSeatAvailability {

    @EmbeddedId
    @NotNull(message = "Seat Availability ID cannot be null")
    @Valid
    private ShowSeatAvailabilityId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("showCode")
    @JoinColumn(name = "show_code")
    @NotNull(message = "Show cannot be null")
    @Valid
    private Show show;

    @NotNull(message = "Availability status cannot be null")
    private Boolean available;


    public ShowSeatAvailabilityId getId() {
        return id;
    }

    public void setId(ShowSeatAvailabilityId id) {
        this.id = id;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}

