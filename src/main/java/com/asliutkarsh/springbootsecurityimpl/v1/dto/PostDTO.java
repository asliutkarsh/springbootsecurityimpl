package com.asliutkarsh.springbootsecurityimpl.v1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;

    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(min = 10, message = "Content must be at least 10 characters long")
    private String content;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
