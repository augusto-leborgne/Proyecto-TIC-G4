package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "show_seat_availability")
public class ShowSeatAvailability {

    @EmbeddedId
    private ShowSeatAvailabilityId id; // Composite key (show_code, seat_column, seat_row)

    @JsonIgnore
    @ManyToOne
    @MapsId("showCode") // This maps the showCode in ShowSeatAvailabilityId to the Show
    @JoinColumn(name = "show_code")
    private Show show;

    private boolean available;

    // Getters and Setters

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

