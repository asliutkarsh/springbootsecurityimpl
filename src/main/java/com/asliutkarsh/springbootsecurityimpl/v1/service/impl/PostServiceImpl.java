package com.asliutkarsh.springbootsecurityimpl.v1.service.impl;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.PostDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public Long createPost(PostDTO postDTO) {
        return 0L;
    }

    @Override
    public PostDTO getPost(Long id) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }

    @Override
    public void updatePost(Long id, PostDTO postDTO) {

    }

    @Override
    public List<PostDTO> getAllPosts() {
        return List.of();
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<PostDTO> getPostsByTitle(String title) {
        return List.of();
    }
}
