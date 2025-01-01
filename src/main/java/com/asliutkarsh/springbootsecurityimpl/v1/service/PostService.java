package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.PostDTO;

import java.util.List;

public interface PostService {
    Long createPost(PostDTO postDTO);
    void deletePost(Long id);
    void updatePost(Long id, PostDTO postDTO);
    PostDTO getPostById(Long id);
    List<PostDTO> getAllPosts();
    List<PostDTO> getPostsByUserId(Long userId);
    List<PostDTO> getPostsByTitle(String title);
}
