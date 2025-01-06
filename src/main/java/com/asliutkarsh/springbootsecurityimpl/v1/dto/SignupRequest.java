package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class SignupRequest {
        //TODO add validation
        private String username;
        private String email;
        private String password;
        private String role;
}
