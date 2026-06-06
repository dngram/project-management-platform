package com.darshan.projectmanagement.backend.controller;

import com.darshan.projectmanagement.backend.dto.TaskRequest;
import com.darshan.projectmanagement.backend.dto.TaskResponse;
import com.darshan.projectmanagement.backend.entity.Task;
import com.darshan.projectmanagement.backend.service.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse createTask(
            @Valid @RequestBody TaskRequest request) {

        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {

        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return "Task deleted successfully";
    }

    @PutMapping("/{taskId}/assign/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public TaskResponse assignTask(
            @PathVariable Long taskId,
            @PathVariable Long userId) {

        return taskService.assignTask(
                taskId,
                userId);
    }

    @GetMapping("/my-tasks")
    public List<TaskResponse> getMyTasks(
            Authentication authentication) {

        return taskService.getMyTasks(
                authentication.getName());
    }
}