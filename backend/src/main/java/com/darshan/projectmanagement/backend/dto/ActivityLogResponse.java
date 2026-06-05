package com.darshan.projectmanagement.backend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ActivityLogResponse {

    private Long id;

    private String action;

    private String username;

    private LocalDateTime createdAt;
}