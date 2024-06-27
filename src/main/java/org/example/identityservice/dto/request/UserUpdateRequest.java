package org.example.identityservice.dto.request;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String password;
    private String firstName, lastName;
    private LocalDate dob;
    Set<String> roles;
}
