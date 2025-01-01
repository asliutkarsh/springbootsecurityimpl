package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private String token;
    private UserDTO user;
}
