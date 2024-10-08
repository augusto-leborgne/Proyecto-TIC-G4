package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @Column(name = "m_name")
    private String movieId;

    @Column(name = "duration")
    private Integer duration;
    @Column(name = "director")
    private String director;

    @Column(name = "minimum_age")
    private Integer minimumAge;

    public String getmovieId() {
        return movieId;
    }

    public void setmovieId(String name) {
        this.movieId = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    // Getters and Setters
}
