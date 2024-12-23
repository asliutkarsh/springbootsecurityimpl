package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @PostMapping(value = "/")
    public ResponseEntity<?> updatePost() {
        return ResponseEntity.ok(new ApiResponse("Update Post", true));
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<?> deletePost() {
        return ResponseEntity.ok(new ApiResponse("Delete Post", true));
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(new ApiResponse("Get All Posts", true));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPostById(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse("Get Post by ID", true));
    }
}
