package org.example.proyectoticg4.dbd.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cinemas")
public class Cinema {

    @Id
    @Column(name = "ci_number")
    private Integer ciNumber;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "halls")
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
