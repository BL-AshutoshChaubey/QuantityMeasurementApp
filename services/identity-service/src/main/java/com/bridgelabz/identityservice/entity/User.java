package com.bridgelabz.identityservice.entity;

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
    private String password;

    public User() {}
    
    // Google OAuth Constructor
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    // Standard Registration Constructor
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

}
