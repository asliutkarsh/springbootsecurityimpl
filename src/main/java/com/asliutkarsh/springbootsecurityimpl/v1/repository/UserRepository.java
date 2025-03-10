package com.asliutkarsh.springbootsecurityimpl.v1.repository;

import com.asliutkarsh.springbootsecurityimpl.v1.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
