package com.asliutkarsh.springbootsecurityimpl.v1.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

/**
 * User entity class
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "user_username_unique", columnNames = "username")})
@Entity
public class User {

    @Id
    @SequenceGenerator(name = "user_id_seq",sequenceName = "user_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_seq")
    private Long id;
    private String username;
    private String email;
    private String password;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
