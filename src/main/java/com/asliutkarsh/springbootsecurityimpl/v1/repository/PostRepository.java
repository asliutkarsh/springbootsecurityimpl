package com.asliutkarsh.springbootsecurityimpl.v1.repository;

import com.asliutkarsh.springbootsecurityimpl.v1.model.Post;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
