package com.darshan.projectmanagement.backend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private Long id;

    private String message;

    private String username;

    private Long taskId;

    private LocalDateTime createdAt;
}