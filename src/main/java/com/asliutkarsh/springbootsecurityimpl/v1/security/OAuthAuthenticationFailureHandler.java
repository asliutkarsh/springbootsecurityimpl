package com.asliutkarsh.springbootsecurityimpl.v1.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Component
@Slf4j
public class OAuthAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String FRONTENDURL = "http://localhost:3000";


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.error("OAuth Authentication Failed: {}", exception.getMessage());

        String redirectUrl = FRONTENDURL + "/oauth/redirect?success=false";
        response.sendRedirect(redirectUrl);

    }
}