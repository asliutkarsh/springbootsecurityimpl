package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.LoginRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authService.register(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader){
        return new ResponseEntity<>(authService.refreshToken(authHeader),HttpStatus.OK);
    }

}
