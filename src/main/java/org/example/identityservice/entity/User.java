package org.example.identityservice.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private String firstName, lastName;
    private LocalDate dob;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, LocalDate dob) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public User(String id, String username, String password, String firstName, String lastName, LocalDate dob) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
}
