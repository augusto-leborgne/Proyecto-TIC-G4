package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;
import org.example.proyectoticg4.dbd.Entities.Hall;

import java.util.Set;

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

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Hall> hallSet;

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
    public Set<Hall> getHallSet() {
        return hallSet;
    }
}
