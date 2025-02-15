package com.asliutkarsh.springbootsecurityimpl.v1.controller;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.ApiResponse;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.PostDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.service.PostService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.data.domain.Page;
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

       // Create a new post
       @PostMapping
       public ResponseEntity<?> createPost(@RequestBody @Valid PostDTO postDTO) {
           Long id = postService.createPost(postDTO);
           ApiResponse<Object> response = new ApiResponse<>(true, "Post Created with ID: " + id, null, null);
           return new ResponseEntity<>(response, HttpStatus.CREATED);
       }
   
       // Update an existing post
       @PutMapping("/{id}")
       public ResponseEntity<?> updatePost(@RequestBody @Valid PostDTO postDTO, @PathVariable Long id) {
           postService.updatePost(id, postDTO);
           ApiResponse<Object> response = new ApiResponse<>(true, "Post Updated", null, null);
           return new ResponseEntity<>(response, HttpStatus.OK);
       }
   
       // Delete a post
       @DeleteMapping("/{id}")
       public ResponseEntity<?> deletePost(@PathVariable Long id) {
           postService.deletePost(id);
           ApiResponse<Object> response = new ApiResponse<>(true, "Post Deleted", null, null);
           return new ResponseEntity<>(response, HttpStatus.OK);
       }
   
       // Get all posts
       @GetMapping
       public ResponseEntity<?> getAllPosts(
                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "asc") String sortDir
       ) {
           Page<PostDTO> posts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
           ApiResponse<Object> response = new ApiResponse<>(true, 
                                                        "Fetched all posts", 
                                                                posts.getContent(),
                                                                    Map.of(
                                                                    "totalPages", posts.getTotalPages(),
                                                                    "totalElements", posts.getTotalElements(),
                                                                    "currentPage", posts.getNumber()
                                                                ));
           return new ResponseEntity<>(response, HttpStatus.OK);
       }
   
       // Get a post by ID
       @GetMapping("/{id}")
       public ResponseEntity<?> getPostById(@PathVariable Long id) {
           PostDTO post = postService.getPostById(id);
           ApiResponse<Object> response = new ApiResponse<>(true, "Post fetched", post, null);
           return new ResponseEntity<>(response, HttpStatus.OK);
       }
   
}
