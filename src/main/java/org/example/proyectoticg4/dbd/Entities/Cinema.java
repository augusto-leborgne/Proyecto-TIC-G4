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

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Hall> hallSet;

    // Getters and Setters
}
