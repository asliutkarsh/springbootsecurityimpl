package com.asliutkarsh.springbootsecurityimpl.v1.service;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean loginSuccess(String username, String password);
    Long registerUser(SignupRequest signupRequest);
    Long createUser(UserDTO userDTO,String password);
    void deleteUser(Long id);
    void updateUser(Long id, UserDTO userDTO);
    void updatePassword(Long id,String password);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);

}
