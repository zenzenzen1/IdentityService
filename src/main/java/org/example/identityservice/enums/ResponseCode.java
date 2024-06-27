package org.example.identityservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(1000, "OK"),
    ;

    private final int code;
    private final String message;
}
