package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    boolean loginSuccess(String username, String password);
    UserDetails createUser(SignupRequest signupRequest);
    void deleteUser(Long id);
    void updateUser(Long id, UserDTO userDTO);
    void updatePassword(Long id,String password);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);

}
