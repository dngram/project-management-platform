package com.darshan.projectmanagement.backend.dto;

import com.darshan.projectmanagement.backend.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private Long projectId;

    private String projectName;

    private Long assignedUserId;

    private String assignedUsername;
}