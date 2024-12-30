package com.asliutkarsh.springbootsecurityimpl.v1.service.impl;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.PostDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.exception.ResourceNotFoundException;
import com.asliutkarsh.springbootsecurityimpl.v1.model.Post;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;
import com.asliutkarsh.springbootsecurityimpl.v1.repository.PostRepository;
import com.asliutkarsh.springbootsecurityimpl.v1.repository.UserRepository;
import com.asliutkarsh.springbootsecurityimpl.v1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

       @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        postRepository.delete(post);
    }

    @Override
    public void updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle()); 
        }
        if (postDTO.getContent() != null) {
            post.setContent(postDTO.getContent());
        }
        postRepository.save(post);
    }

    @Override
    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return postRepository.findByUser(user).stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();
    }

    @Override
    public List<PostDTO> getPostsByTitle(String title) {
        return postRepository.findByTitleContaining(title).stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();
    }
}
