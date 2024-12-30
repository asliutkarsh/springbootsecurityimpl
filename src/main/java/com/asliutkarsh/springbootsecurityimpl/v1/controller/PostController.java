package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.PostDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        Long id = postService.createPost(postDTO);
        return new ResponseEntity<>(new ApiResponse("Post Created with ID: " + id, true), HttpStatus.CREATED);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updatePost(@RequestBody PostDTO postDTO, @RequestParam Long id) {
        postService.updatePost(id, postDTO);
        return new ResponseEntity<>(new ApiResponse("Post Updated", true), HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post Deleted", true), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }
}
