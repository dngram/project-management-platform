package com.darshan.projectmanagement.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private String message;

    private boolean read;

    private LocalDateTime createdAt;
}