package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login() {
        return ResponseEntity.ok(new ApiResponse("Login", true));
    }

    @GetMapping(value = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new ApiResponse("Logout", true));
    }

    @PostMapping(value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register() {
        return ResponseEntity.ok(new ApiResponse("Register", true));
    }
}
