package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class  User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "age")
    private Integer age;

    // Getters and Setters
}
