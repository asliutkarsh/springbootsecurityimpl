package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @RequestHeader Long id) {
        userService.updateUser(id, userDTO);
        return new ResponseEntity<>(new ApiResponse("User Updated", true), HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User Deleted", true), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getMe(@RequestHeader String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
}
