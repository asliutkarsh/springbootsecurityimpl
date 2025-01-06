package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    boolean loginSuccess(String username, String password);
    UserDetails createUser(SignupRequest signupRequest);
    void deleteUser(Long id);
    void updateUser(Long id, UserDTO userDTO);
    void updatePassword(Long id,String password);
    Page<UserDTO> getAllUsers(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);

}
