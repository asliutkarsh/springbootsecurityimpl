package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.LoginRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        return new ResponseEntity<>(userService.loginSuccess(loginRequest.getUsername(),loginRequest.getPassword()), HttpStatus.OK);
    }

    @GetMapping(value = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestHeader String Authorization) {
        System.out.println(Authorization);
        return ResponseEntity.ok(new ApiResponse("Logout Done", true));
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        Long id = userService.registerUser(signupRequest);
        return new ResponseEntity<>(new ApiResponse("User Registered with ID: " + id, true), HttpStatus.CREATED);
    }
}
