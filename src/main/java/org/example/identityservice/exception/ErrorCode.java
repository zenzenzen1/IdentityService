package org.example.identityservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1001, "Username already existed"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    USERNAME_INVALID(1002, "Username must be at least 3 characters"),
    INVALID_KEY(1003, "Invalid message key"),
    USER_NOT_FOUND(1004, "User not found"),
    UNAUTHENTICATED(1005, "Unauthenticated"),
    INVALID_TOKEN_JSON_OBJECT(1006, "Invalid token"),
    ;
    
    
    private final int code;
    private final String message;
    
}
