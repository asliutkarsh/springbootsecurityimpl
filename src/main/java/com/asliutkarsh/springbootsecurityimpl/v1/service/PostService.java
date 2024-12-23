package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.PostDTO;

import java.util.List;

public interface PostService {
    Long createPost(PostDTO postDTO);
    PostDTO getPost(Long id);
    void deletePost(Long id);
    void updatePost(Long id, PostDTO postDTO);
    List<PostDTO> getAllPosts();
    List<PostDTO> getPostsByUserId(Long userId);
    List<PostDTO> getPostsByTitle(String title);
}
