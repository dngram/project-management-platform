package com.darshan.projectmanagement.backend.dto;

import com.darshan.projectmanagement.backend.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private UserRole role;

    private LocalDateTime createdAt;
}