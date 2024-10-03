package org.example.proyectoticg4.dbd.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class  User {

    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "enc_password")
    private String password;


    public String getuserId() {
        return userId;
    }

    public void setuserId(String id) {
        this.userId = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
