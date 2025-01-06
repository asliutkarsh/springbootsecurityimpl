package com.asliutkarsh.springbootsecurityimpl.v1.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.AuthenticationResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.LoginRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.enums.SessionPolicy;
import com.asliutkarsh.springbootsecurityimpl.v1.exception.WrongInputException;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;
import com.asliutkarsh.springbootsecurityimpl.v1.service.AuthService;
import com.asliutkarsh.springbootsecurityimpl.v1.service.TokenService;
import com.asliutkarsh.springbootsecurityimpl.v1.service.UserService;
import com.asliutkarsh.springbootsecurityimpl.v1.utils.JwtTokenUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    public AuthServiceImpl(UserService userService, AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, ModelMapper modelMapper,
            TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    @Override
    public AuthenticationResponse register(SignupRequest signupRequest) {
        UserDetails userDetails = userService.createUser(signupRequest);
        String token = jwtTokenUtil.generatedToken(userDetails);
        String refreshToken = jwtTokenUtil.generatedRefreshToken(userDetails);

        // First time registration, so no need to check for existing sessions
        tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.MULTIPLE_SESSIONS);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(modelMapper.map(userDetails, UserDTO.class))
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenUtil.generatedToken(userDetails);
        String refreshToken = jwtTokenUtil.generatedRefreshToken(userDetails);
        
        // Choose Approach
        // Approach 1 - Multiple Sessions
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.MULTIPLE_SESSIONS);

        // Approach 2 - Revoke All Sessions
        tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.REVOKE_ALL_SESSIONS);

        // Approach 3 - Single Session
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.REPLACE_PREVIOUS_SESSION);

        // Approach 4 - Revoke Oldest Session If Max Reached
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.REVOKE_OLDEST_SESSION_IF_MAX_REACHED);

        // Approach 5 - Deny New Session If Max Reached
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.DENY_NEW_SESSION_IF_MAX_REACHED);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(modelMapper.map(userDetails, UserDTO.class))
                .build();
    }

    @Override
    public void changePassword(String token, String oldPassword, String newPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }


    @Override
    public AuthenticationResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new WrongInputException("Authorization header is missing or malformed");
        }
    
        String refreshToken = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        boolean isTokenPresent = tokenService.isRefreshTokenPresent(refreshToken);

        // Check if the refresh token is valid and present
        if (!jwtTokenUtil.validateToken(refreshToken, userDetails) || !isTokenPresent) {
            throw new WrongInputException("Invalid refresh token");
        }
    
        // Generate new access token and refresh token
        String token = jwtTokenUtil.generatedToken(userDetails);
        String newRefreshToken = jwtTokenUtil.generatedRefreshToken(userDetails);
    
        // Choose Approach
        // Approach 1 - Multiple Sessions
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.MULTIPLE_SESSIONS);

        // Approach 2 - Revoke All Sessions
        tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.REVOKE_ALL_SESSIONS);

        // Approach 3 - Single Session
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.REPLACE_PREVIOUS_SESSION);


        // Approach 4 - Revoke Oldest Session If Max Reached
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.REVOKE_OLDEST_SESSION_IF_MAX_REACHED);

        // Approach 5 - Deny New Session If Max Reached
        // tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class), SessionPolicy.DENY_NEW_SESSION_IF_MAX_REACHED);
    
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(newRefreshToken)
                .user(modelMapper.map(userDetails, UserDTO.class))
                .build();
    }    


    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new WrongInputException();
        }
    }

}
