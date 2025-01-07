package com.asliutkarsh.springbootsecurityimpl.v1.service.impl;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.dto.UserDTO;
import com.asliutkarsh.springbootsecurityimpl.v1.enums.Role;
import com.asliutkarsh.springbootsecurityimpl.v1.exception.DuplicationEntryException;
import com.asliutkarsh.springbootsecurityimpl.v1.exception.ResourceNotFoundException;
import com.asliutkarsh.springbootsecurityimpl.v1.model.User;
import com.asliutkarsh.springbootsecurityimpl.v1.repository.UserRepository;
import com.asliutkarsh.springbootsecurityimpl.v1.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean loginSuccess(String username, String password) {
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getPassword().equals(password);
    }

    @Override
    public UserDetails createUser(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new DuplicationEntryException("Username already exists");
        }

        Role role = signupRequest.getRole() == null ? Role.USER : Role.valueOf(signupRequest.getRole().toUpperCase());

        User user = User.builder()
                    .username(signupRequest.getUsername())
                    .email(signupRequest.getEmail())
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .role(role)
                    .build();
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userDTO.getUsername() != null && !Objects.equals(user.getUsername(), userDTO.getUsername()) && userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DuplicationEntryException("Username already exists");
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        userRepository.save(user);
    }

    @Override
    public void updatePassword(Long id, String password) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }


    @Override
    public Page<UserDTO> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> users = userRepository.findAll(pageable);

        return users.map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }
}
