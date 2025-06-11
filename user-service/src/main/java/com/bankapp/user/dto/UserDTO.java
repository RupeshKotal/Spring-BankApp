package com.bankapp.user.dto;

import com.bankapp.user.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 