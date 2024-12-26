package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String email;
}
