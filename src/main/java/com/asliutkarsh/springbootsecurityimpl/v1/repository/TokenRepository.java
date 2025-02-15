package com.asliutkarsh.springbootsecurityimpl.v1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;   
import com.asliutkarsh.springbootsecurityimpl.v1.model.Token;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;


@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByRefreshToken(String refreshToken);
    // void delete(Token tokenEntity);
    Optional<Token> findByUser(User user);
    void deleteByUser(User user);
    long countByUser(User user);
    List<Token> findByUserOrderByCreatedAtDesc(User user);
    
}
