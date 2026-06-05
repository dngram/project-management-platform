package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.CommentRequest;
import com.darshan.projectmanagement.backend.dto.CommentResponse;
import com.darshan.projectmanagement.backend.entity.Comment;
import com.darshan.projectmanagement.backend.entity.Task;
import com.darshan.projectmanagement.backend.entity.User;
import com.darshan.projectmanagement.backend.exception.TaskNotFoundException;
import com.darshan.projectmanagement.backend.repository.CommentRepository;
import com.darshan.projectmanagement.backend.repository.TaskRepository;
import com.darshan.projectmanagement.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final NotificationService notificationService;

    public CommentService(
            CommentRepository commentRepository,
            TaskRepository taskRepository,
            UserRepository userRepository, ActivityLogService activityLogService, NotificationService notificationService) {

        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
        this.notificationService = notificationService;
    }

    private CommentResponse mapToResponse(Comment comment) {

        return CommentResponse.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .username(comment.getUser().getUsername())
                .taskId(comment.getTask().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public CommentResponse addComment(
            Long taskId,
            String email,
            CommentRequest request) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(taskId));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Comment comment = Comment.builder()
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .task(task)
                .user(user)
                .build();

        comment = commentRepository.save(comment);
        if (task.getAssignedUser() != null) {

            notificationService.createNotification(
                    task.getAssignedUser(),
                    "New comment on task: "
                            + task.getTitle()
            );
        }
        activityLogService.log(
                task,
                user,
                "Comment added: " + request.getMessage()
        );

        return mapToResponse(comment);
    }

    public List<CommentResponse> getTaskComments(Long taskId) {

        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}