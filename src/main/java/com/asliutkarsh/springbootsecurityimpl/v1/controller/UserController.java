package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.service.UserService;
import com.asliutkarsh.springbootsecurityimpl.v1.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        userService.updateUser(id, userDTO);
        ApiResponse<Object> response = new ApiResponse<>(true, "User Updated", null, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponse<Object> response = new ApiResponse<>(true, "User Deleted", null, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all users
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        Object users = userService.getAllUsers();
        ApiResponse<Object> response = new ApiResponse<>(true, "Fetched all users", users, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get the logged-in user's information
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        UserDTO userDTO = userService.getUserByUsername(Utils.getAuthenticatedUserName());
        ApiResponse<Object> response = new ApiResponse<>(true, "Current User fetched", userDTO, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
