package com.asliutkarsh.springbootsecurityimpl.v1.security;

import com.asliutkarsh.springbootsecurityimpl.v1.service.TokenService;
import com.asliutkarsh.springbootsecurityimpl.v1.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, TokenService tokenService) {
        this.tokenService = tokenService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Get the Bearer token from the request header
        String header = request.getHeader("Authorization");
        String username = null;
        String token = null;

        // Check for valid "Bearer" token format
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);  // Extract the token
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);  // Extract username from token
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token", e);
            } catch (ExpiredJwtException e) {
                log.error("JWT Token has expired", e);
            } catch (MalformedJwtException e) {
                log.error("Malformed JWT Token", e);
            } catch (JwtException e) {
                log.error("JWT Exception: Invalid token", e);
            }
        } else {
            log.error("JWT Token does not begin with 'Bearer' or is not present");
        }

        // Proceed if token is valid, username is extracted, and no existing authentication is found
        if (username != null && token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // Load user details
            boolean isTokenPresent = tokenService.isTokenPresent(token);

            // Validate the token
            if (jwtTokenUtil.validateToken(token, userDetails) && isTokenPresent) {
                // If token is valid, authenticate the user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);  // Set the user authentication in SecurityContext
            } else {
                log.error("Invalid JWT Token");
                throw new JwtException("Invalid Token");
            }
        }
        
        filterChain.doFilter(request, response);  // Continue the filter chain
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        // Bypass JWT authentication for certain endpoints (e.g., authentication and health check endpoints)
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth") || path.startsWith("/actuator/health") || path.startsWith("/api-docs");
    }
}
