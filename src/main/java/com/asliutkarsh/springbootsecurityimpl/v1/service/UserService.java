package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;

import java.util.List;

public interface UserService {

    Long createUser(UserDTO userDTO);
    void deleteUser(Long id);
    void updateUser(Long id, UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);

}
