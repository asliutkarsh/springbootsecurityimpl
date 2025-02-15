package com.asliutkarsh.springbootsecurityimpl.v1.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asliutkarsh.springbootsecurityimpl.v1.repository.TokenRepository;
import com.asliutkarsh.springbootsecurityimpl.v1.repository.UserRepository;
import com.asliutkarsh.springbootsecurityimpl.v1.service.TokenService;
import com.asliutkarsh.springbootsecurityimpl.v1.utils.JwtTokenUtil;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import com.asliutkarsh.springbootsecurityimpl.v1.enums.SessionPolicy;
import com.asliutkarsh.springbootsecurityimpl.v1.exception.MaxSessionsExceededException;
import com.asliutkarsh.springbootsecurityimpl.v1.exception.ResourceNotFoundException;
import com.asliutkarsh.springbootsecurityimpl.v1.model.Token;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;


@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private static final int MAX_SESSIONS = 3;

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository, UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public Long saveToken(String token, String refreshToken, User user, SessionPolicy sessionPolicy) {

        Token tokenEntity = Token.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();

        if (sessionPolicy == SessionPolicy.REPLACE_PREVIOUS_SESSION) {
            replaceSession(user, token, refreshToken);            
        } else if (sessionPolicy == SessionPolicy.REVOKE_ALL_SESSIONS) {
            revokeAllSessions(user);
        } else if (sessionPolicy == SessionPolicy.REVOKE_OLDEST_SESSION_IF_MAX_REACHED && isMaxSessionsExceeded(user)){
                revokeOldestSession(user);
        } else if (sessionPolicy == SessionPolicy.DENY_NEW_SESSION_IF_MAX_REACHED && isMaxSessionsExceeded(user)){
                throw new MaxSessionsExceededException("Already have " + MAX_SESSIONS + " Sessions");
        }
            
        // SessionPolicy.MULTIPLE_SESSIONS
        Token savedToken = tokenRepository.save(tokenEntity);
        return savedToken.getId();
    }

    @Override
    public boolean isTokenPresent(String token) {
        return tokenRepository.findByToken(token).isPresent();
    }

    
    @Override
    public boolean isRefreshTokenPresent(String refreshToken) {
        return tokenRepository.findByToken(refreshToken).isPresent();
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        Token tokenEntity = tokenRepository.findByToken(token)
                    .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        tokenRepository.delete(tokenEntity);
    }

    private void revokeAllSessions(User user) {
        tokenRepository.deleteByUser(user);
    }


    private void replaceSession(User user, String newToken, String newRefreshToken) {   
        // Find Token for previous session or else add new Token
        Token token = tokenRepository.findByUser(user)
                            .orElse(Token.builder().user(user).build());
                    
        token.setToken(newToken);
        token.setRefreshToken(newRefreshToken);
        tokenRepository.save(token);
    }


    // Call in Save Token
    private boolean isMaxSessionsExceeded(User user){
        return tokenRepository.countByUser(user) >= MAX_SESSIONS;
    }


    // Revoke Oldest if MAX_SESSIONS
    private void revokeOldestSession(User user){
        List<Token> tokens = tokenRepository.findByUserOrderByCreatedAtDesc(user);
        tokenRepository.delete(tokens.get(tokens.size()-1));
    }

    
}
