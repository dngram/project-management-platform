package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.TaskRequest;
import com.darshan.projectmanagement.backend.dto.TaskResponse;
import com.darshan.projectmanagement.backend.entity.Project;
import com.darshan.projectmanagement.backend.entity.Task;
import com.darshan.projectmanagement.backend.entity.User;
import com.darshan.projectmanagement.backend.exception.ProjectNotFoundException;
import com.darshan.projectmanagement.backend.exception.TaskNotFoundException;
import com.darshan.projectmanagement.backend.exception.UserNotFoundException;
import com.darshan.projectmanagement.backend.repository.ProjectRepository;
import com.darshan.projectmanagement.backend.repository.TaskRepository;
import com.darshan.projectmanagement.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final NotificationService notificationService;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,  UserRepository userRepository, ActivityLogService activityLogService, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
        this.notificationService = notificationService;
    }

    public TaskResponse createTask(TaskRequest request) {

        Project project = projectRepository.findById(
                        request.getProjectId())
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                request.getProjectId()));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .project(project)
                .build();

        task = taskRepository.save(task);

        return mapToResponse(task);
    }

    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Task getTaskById(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public TaskResponse updateTask(Long id, Task updatedTask) {

        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        Task savedTask =
                taskRepository.save(existingTask);

        return mapToResponse(savedTask);
    }

    public void deleteTask(Long id) {

        Task task = getTaskById(id);

        taskRepository.delete(task);
    }

    public TaskResponse assignTask(
            Long taskId,
            Long userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(userId));

        task.setAssignedUser(user);

        task = taskRepository.save(task);
        notificationService.createNotification(
                user,
                "You have been assigned task: "
                        + task.getTitle()
        );
        activityLogService.log(
                task,
                user,
                "Task assigned to " + user.getUsername()
        );

        return mapToResponse(task);
    }

    private TaskResponse mapToResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .assignedUserId(
                        task.getAssignedUser() != null
                                ? task.getAssignedUser().getId()
                                : null
                )
                .assignedUsername(
                        task.getAssignedUser() != null
                                ? task.getAssignedUser().getUsername()
                                : null
                )
                .build();
    }

    public List<TaskResponse> getMyTasks(String email) {

        return taskRepository
                .findByAssignedUserEmail(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}