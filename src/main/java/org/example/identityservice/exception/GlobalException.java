package org.example.identityservice.exception;

import org.example.identityservice.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;
import java.util.Objects;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<String>> handleRuntimeException(final RuntimeException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(e.getMessage());
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(value = ParseException.class)
    ResponseEntity<ApiResponse<String>> handleParseException(final ParseException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.INVALID_TOKEN_JSON_OBJECT.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_TOKEN_JSON_OBJECT.getMessage());
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<String> apiResponse = new ApiResponse<>();
        
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(value = IllegalStateException.class)
    ResponseEntity<String> handleIllegalStateException(final IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        
        ErrorCode errorCode;
        try {
            errorCode= ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException illegalArgumentException) {
              errorCode = ErrorCode.INVALID_KEY;
        }
        
        ApiResponse<String> apiResponse = new ApiResponse<>();
        
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
