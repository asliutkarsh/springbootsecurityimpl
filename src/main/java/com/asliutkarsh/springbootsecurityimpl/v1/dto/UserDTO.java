package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private Long id;
    private String username;
    private String email;
}
