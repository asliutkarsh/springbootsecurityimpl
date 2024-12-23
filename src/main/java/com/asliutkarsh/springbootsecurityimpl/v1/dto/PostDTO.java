package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import lombok.Data;

@Data
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private Long userId;
}
