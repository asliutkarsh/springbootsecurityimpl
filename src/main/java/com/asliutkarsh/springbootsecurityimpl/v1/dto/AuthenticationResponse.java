package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private UserDTO user;
}
