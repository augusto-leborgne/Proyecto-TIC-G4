package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m_show")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_cod")
    private Integer showCode;

    @ManyToOne
    @JoinColumn(name = "m_name", referencedColumnName = "m_name", nullable = false)
    @NotNull(message = "Movie cannot be null")
    @Valid
    private Movie movie;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ci_number", referencedColumnName = "cinemaNumber", nullable = false),
            @JoinColumn(name = "h_number", referencedColumnName = "hNumber", nullable = false)
    })
    @NotNull(message = "Hall cannot be null")
    @Valid
    private Hall hall;

    @Column(name = "show_time", nullable = false)
    @NotNull(message = "Show time cannot be null")
    private LocalDateTime showTime;

    @JsonIgnore
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private List<ShowSeatAvailability> showSeatAvailabilities = new ArrayList<>();

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price cannot be negative")
    private Integer price;

    public Integer getShowCode() {
        return showCode;
    }

    public void setShowCode(Integer showCode) {
        this.showCode = showCode;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<ShowSeatAvailability> getShowSeatAvailabilities() {
        return showSeatAvailabilities;
    }

    public void setShowSeatAvailabilities(List<ShowSeatAvailability> showSeatAvailabilities) {
        this.showSeatAvailabilities = showSeatAvailabilities;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
