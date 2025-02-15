package com.asliutkarsh.springbootsecurityimpl.v1.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.asliutkarsh.springbootsecurityimpl.v1.service.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogoutService implements LogoutHandler {

    private final TokenService tokenService;

    public LogoutService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader("Authorization");
        String token = null;
        if (header == null || !header.startsWith("Bearer")){
            return;
        }
        token = header.substring(7);
        tokenService.deleteToken(token);
        SecurityContextHolder.clearContext();
        log.info("User logged out successfully");
    }
    
}
