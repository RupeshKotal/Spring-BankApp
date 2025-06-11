package com.bankapp.user.controller;

import com.bankapp.user.dto.AuthRequest;
import com.bankapp.user.dto.AuthResponse;
import com.bankapp.user.dto.UserDTO;
import com.bankapp.user.model.User;
import com.bankapp.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for user management and authentication")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login user")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user details")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getCurrentUser(authentication.getName()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user details")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
} 