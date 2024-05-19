package org.example.identityservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;
//    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    private String firstName, lastName;
    private LocalDate dob;


}
