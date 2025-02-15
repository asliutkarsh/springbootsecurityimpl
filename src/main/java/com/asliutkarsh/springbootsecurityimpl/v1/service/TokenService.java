package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.enums.SessionPolicy;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;

public interface TokenService {
    Long saveToken(String token, String refreshToken, User user, SessionPolicy sessionPolicy);
    void deleteToken(String token);
    boolean isTokenPresent(String token);
    boolean isRefreshTokenPresent(String refreshToken);
}
