package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping(value = "/")
    public ResponseEntity<?> updateUser() {
        return ResponseEntity.ok(new ApiResponse("Update User", true));
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok(new ApiResponse("Delete User", true));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getMe() {
        return ResponseEntity.ok(new ApiResponse("Get Me", true));
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse("Get All Users", true));
    }
}
