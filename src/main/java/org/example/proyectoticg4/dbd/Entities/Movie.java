package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;

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

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;  // To store the image as binary data

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String name) {
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

    public byte[] getImage() { return image; }

    public void setImage(byte[] image) { this.image = image; }
}
