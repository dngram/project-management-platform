package com.darshan.projectmanagement.backend.controller;

import com.darshan.projectmanagement.backend.dto.NotificationResponse;
import com.darshan.projectmanagement.backend.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @GetMapping
    public List<NotificationResponse>
    getMyNotifications(
            Authentication authentication) {

        return notificationService
                .getMyNotifications(
                        authentication.getName());
    }

    @PutMapping("/{id}/read")
    public void markAsRead(
            @PathVariable Long id) {

        notificationService.markAsRead(id);
    }

    @PutMapping("/read-all")
    public void markAllAsRead(
            Authentication authentication) {

        notificationService.markAllAsRead(
                authentication.getName());
    }
}