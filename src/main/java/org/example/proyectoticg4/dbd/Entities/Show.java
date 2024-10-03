package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_show")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_cod")
    private Integer showCode; // Unique show code

    @ManyToOne
    @JoinColumn(name = "m_name", referencedColumnName = "m_name", nullable = false)
    private Movie movie; // Reference to the movie

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ci_number", referencedColumnName = "cinemaNumber", nullable = false),
            @JoinColumn(name = "h_number", referencedColumnName = "hNumber", nullable = false)
    })
    private Hall hall; // Reference to the hall

    @Column(name = "show_time", nullable = false)
    private LocalDateTime showTime; // Time of the show

    // Getters and Setters
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
}
