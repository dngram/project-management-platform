package com.darshan.projectmanagement.backend.controller;

import com.darshan.projectmanagement.backend.dto.ActivityLogResponse;
import com.darshan.projectmanagement.backend.service.ActivityLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/activity")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(
            ActivityLogService activityLogService) {

        this.activityLogService = activityLogService;
    }

    @GetMapping
    public List<ActivityLogResponse> getTaskActivity(
            @PathVariable Long taskId) {

        return activityLogService.getTaskActivity(taskId);
    }
}