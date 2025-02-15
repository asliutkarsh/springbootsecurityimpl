package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.PostDTO;

import java.util.List;

import org.springframework.data.domain.Page;

public interface PostService {
    Long createPost(PostDTO postDTO);
    void deletePost(Long id);
    void updatePost(Long id, PostDTO postDTO);
    PostDTO getPostById(Long id);
    Page<PostDTO> getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir);
    List<PostDTO> getPostsByUserId(Long userId);
    List<PostDTO> getPostsByTitle(String title);
}
