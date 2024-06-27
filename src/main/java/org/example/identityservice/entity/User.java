package org.example.identityservice.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
// import org.example.identityservice.enums.Role;

@Entity
@Data
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
@Builder
@AllArgsConstructor
// @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String password;
    private String firstName, lastName;
    private LocalDate dob;

    @ManyToMany
    private Set<Role> roles;
}
