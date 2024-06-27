package org.example.identityservice.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import org.example.identityservice.validator.DobConstraint;

import lombok.Data;

@Data
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;

    // @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String firstName, lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;
}
