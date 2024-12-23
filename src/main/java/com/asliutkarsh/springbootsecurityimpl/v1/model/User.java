package com.asliutkarsh.springbootsecurityimpl.v1.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * User entity class
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
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


}
