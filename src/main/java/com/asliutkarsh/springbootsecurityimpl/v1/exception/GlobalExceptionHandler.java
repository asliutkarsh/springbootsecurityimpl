package com.asliutkarsh.springbootsecurityimpl.v1.exception;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicationEntryException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicationEntryException(DuplicationEntryException e) {
        log.error("Duplication Entry Exception: {}", e.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(false, e.getMessage(), null, null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("Resource Not Found Exception: {}", e.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(false, e.getMessage(), null, null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongInputException.class)
    public ResponseEntity<ApiResponse<Object>> handleWrongInputException(WrongInputException e) {
        log.error("Wrong Input Exception: {}", e.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(false, e.getMessage(), null, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtException(JwtException e) {
        log.error("JWT Exception: {}", e.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(false, "Invalid JWT", null, null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MaxSessionsExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxSessionsExceededException(MaxSessionsExceededException e) {
        log.error("Max Sessions Exceeded Exception: {}", e.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(false, e.getMessage(), null, null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(false, "There is some error with server right now", null, null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    
}
