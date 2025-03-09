package com.asliutkarsh.springbootsecurityimpl.v1.security;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.enums.SessionPolicy;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;
import com.asliutkarsh.springbootsecurityimpl.v1.service.TokenService;
import com.asliutkarsh.springbootsecurityimpl.v1.service.UserService;
import com.asliutkarsh.springbootsecurityimpl.v1.utils.JwtTokenUtil;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    private static final String FRONTENDURL = "http://localhost:3000";

    public OAuthAuthenticationSuccessHandler(UserService userService, JwtTokenUtil jwtTokenUtil,
            ModelMapper modelMapper, TokenService tokenService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {
            var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

            UserDetails userDetails = null;
            String email = null;
            String name = null;
            String provider = "";

            if (oauth2AuthenicationToken.getAuthorizedClientRegistrationId().equals("google")) {
                email = user.getAttribute("email").toString();
                name = user.getAttribute("name").toString();
                provider = "GOOGLE";
            } else if (oauth2AuthenicationToken.getAuthorizedClientRegistrationId().equals("github")) {
                email = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                        : user.getAttribute("login").toString() + "@gmail.com";
                name = user.getAttribute("login").toString();
                provider = "GITHUB";
            }

            UserDTO existingUser = userService.getUserByEmailOptional(email).orElse(null);

            if (existingUser == null) {
                SignupRequest newUser = SignupRequest.builder()
                        .email(email)
                        .username(generateUsername(name))
                        .provider(provider.equals("GITHUB") ? "GITHUB" : "GOOGLE")
                        .role("USER")
                        .password(generatePassword())
                        .build();
                userDetails = userService.createUser(newUser);
            } else {
                userDetails = modelMapper.map(existingUser, User.class);
            }

            // Generate JWT token
            String token = jwtTokenUtil.generatedToken(userDetails);
            String refreshToken = jwtTokenUtil.generatedRefreshToken(userDetails);
            tokenService.saveToken(token, refreshToken, modelMapper.map(userDetails, User.class),
                    SessionPolicy.MULTIPLE_SESSIONS);

            String redirectUrl = FRONTENDURL + "/oauth/redirect?success=true";

            Cookie tokenCookie = new Cookie("accessToken", token);
            // tokenCookie.setHttpOnly(true);
            // tokenCookie.setSecure(true);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(100);

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            // refreshTokenCookie.setHttpOnly(true);
            // refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(100);

            response.addCookie(tokenCookie);
            response.addCookie(refreshTokenCookie);
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            response.sendRedirect(FRONTENDURL + "/oauth/redirect?success=false");
        }
    }

    private String generateUsername(String name) {
        // replace spaces with empty string and convert to lowercase add random number
        // if username already exists
        name = name.toLowerCase().replace(" ", "");
        while (userService.getUserByUsernameOptional(name).isPresent()) {
            log.info("Username already exists, adding random number to username");
            name += (int) (Math.random() * 100);
        }
        return name;
    }

    private String generatePassword() {
        // generate random password with 8 characters
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int random = (int) (Math.random() * 4);
            switch (random) {
                case 0:
                    password.append((char) ((int) (Math.random() * 26) + 65)); // Uppercase
                    break;
                case 1:
                    password.append((char) ((int) (Math.random() * 26) + 97)); // Lowercase
                    break;
                case 2:
                    password.append((char) ((int) (Math.random() * 10) + 48)); // Digit
                    break;
                case 3:
                    password.append((char) ((int) (Math.random() * 15) + 33)); // Special Character
                    break;
            }
        }
        return password.toString();
    }
}