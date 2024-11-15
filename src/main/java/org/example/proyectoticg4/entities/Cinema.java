package org.example.proyectoticg4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Entity
@Table(name = "cinemas")
public class Cinema {

    @Id
    @Column(name = "ci_number")
    @NotNull(message = "Cinema number cannot be null")
    private Integer ciNumber;

    @Column(name = "neighborhood")
    @NotBlank(message = "Neighborhood cannot be blank")
    private String neighborhood;

    @Column(name = "halls")
    @NotNull(message = "Number of halls cannot be null")
    @Positive(message = "Number of halls must be positive")
    private Integer halls;

    @JsonIgnore
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hall> hallSet;

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getCiNumber() {
        return ciNumber;
    }

    public void setCiNumber(Integer ciNumber) {
        this.ciNumber = ciNumber;
    }

    public Integer getHalls() {
        return halls;
    }

    public void setHalls(Integer halls) {
        this.halls = halls;
    }

    public List<Hall> getHallSet() {
        return hallSet;
    }
}
