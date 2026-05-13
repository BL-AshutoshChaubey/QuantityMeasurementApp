package com.bridgelabz.quantitymeasurementApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;
    private String role = "ROLE_USER";

    public User() {}
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
