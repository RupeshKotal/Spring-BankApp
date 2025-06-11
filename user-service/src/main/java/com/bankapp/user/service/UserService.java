package com.bankapp.user.service;

import com.bankapp.user.dto.AuthRequest;
import com.bankapp.user.dto.AuthResponse;
import com.bankapp.user.dto.UserDTO;
import com.bankapp.user.model.User;

public interface UserService {
    UserDTO register(User user);
    AuthResponse login(AuthRequest request);
    UserDTO getCurrentUser(String username);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
} 