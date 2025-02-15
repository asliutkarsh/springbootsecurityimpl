package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.AuthenticationResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.LoginRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;

public interface AuthService {
    AuthenticationResponse register(SignupRequest signupRequest);

    AuthenticationResponse login(LoginRequest loginRequest);

    void changePassword(String token, String oldPassword, String newPassword);   

    AuthenticationResponse refreshToken(String authHeader);
}
