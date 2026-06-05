package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.ActivityLogResponse;
import com.darshan.projectmanagement.backend.entity.ActivityLog;
import com.darshan.projectmanagement.backend.entity.Task;
import com.darshan.projectmanagement.backend.entity.User;
import com.darshan.projectmanagement.backend.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(
            ActivityLogRepository activityLogRepository) {

        this.activityLogRepository = activityLogRepository;
    }

    public void log(
            Task task,
            User user,
            String action) {

        ActivityLog activityLog =
                ActivityLog.builder()
                        .action(action)
                        .createdAt(LocalDateTime.now())
                        .task(task)
                        .user(user)
                        .build();

        activityLogRepository.save(activityLog);
    }

    private ActivityLogResponse mapToResponse(
            ActivityLog log) {

        return ActivityLogResponse.builder()
                .id(log.getId())
                .action(log.getAction())
                .username(log.getUser().getUsername())
                .createdAt(log.getCreatedAt())
                .build();
    }

    public List<ActivityLogResponse> getTaskActivity(
            Long taskId) {

        return activityLogRepository
                .findByTaskIdOrderByCreatedAtDesc(taskId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}