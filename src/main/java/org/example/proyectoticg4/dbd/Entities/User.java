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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
