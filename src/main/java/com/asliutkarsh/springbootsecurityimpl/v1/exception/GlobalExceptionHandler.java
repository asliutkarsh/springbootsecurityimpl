package com.asliutkarsh.springbootsecurityimpl.v1.exception;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public ResponseEntity<?> handleDuplicationEntryException(DuplicationEntryException e) {
        log.error("Duplication Entry Exception: {}", e.getMessage());
        return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("Resource Not Found Exception: {}", e.getMessage());
        return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
