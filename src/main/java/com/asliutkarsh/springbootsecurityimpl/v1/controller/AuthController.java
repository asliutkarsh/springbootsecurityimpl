package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.LoginRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.TokenResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.exception.WrongInputException;
import com.asliutkarsh.springbootsecurityimpl.v1.service.UserService;
import com.asliutkarsh.springbootsecurityimpl.v1.utils.JwtTokenUtil;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        authenticate(loginRequest.getUsername(),loginRequest.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenUtil.generatedToken(userDetails);

        TokenResponse response = new TokenResponse();
        response.setToken(token);
        response.setUser(modelMapper.map(userDetails,UserDTO.class));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        Long id = userService.registerUser(signupRequest);

        ApiResponse<Long> response = new ApiResponse<>(true, "User Created", id, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw  new WrongInputException();
        }
    }

}
