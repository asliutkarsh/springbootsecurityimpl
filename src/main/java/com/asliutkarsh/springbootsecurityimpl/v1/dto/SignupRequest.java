package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import lombok.Data;
@Data
public class SignupRequest {
        //TODO add validation
        private String username;
        private String email;
        private String password;
}
