package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "m_name")
    private String name;

    @Column(name = "duration")
    private Integer duration;
    @Column(name = "director")
    private String director;

    @Column(name = "minimum_age")
    private Integer minimumAge;

    // Getters and Setters
}
