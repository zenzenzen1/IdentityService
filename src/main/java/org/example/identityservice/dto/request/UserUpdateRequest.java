package org.example.identityservice.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private String password;
    private String firstName, lastName;
    private LocalDate dob;
}
