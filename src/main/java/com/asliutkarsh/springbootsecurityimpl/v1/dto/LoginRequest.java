package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;
}
