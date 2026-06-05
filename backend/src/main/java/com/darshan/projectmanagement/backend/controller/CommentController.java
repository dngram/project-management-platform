package com.darshan.projectmanagement.backend.controller;

import com.darshan.projectmanagement.backend.dto.CommentRequest;
import com.darshan.projectmanagement.backend.dto.CommentResponse;
import com.darshan.projectmanagement.backend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(
            CommentService commentService) {

        this.commentService = commentService;
    }

    @PostMapping
    public CommentResponse addComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication) {

        return commentService.addComment(
                taskId,
                authentication.getName(),
                request);
    }

    @GetMapping
    public List<CommentResponse> getComments(
            @PathVariable Long taskId) {

        return commentService.getTaskComments(taskId);
    }
}