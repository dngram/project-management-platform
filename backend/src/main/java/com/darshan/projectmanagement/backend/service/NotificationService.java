package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.NotificationResponse;
import com.darshan.projectmanagement.backend.entity.Notification;
import com.darshan.projectmanagement.backend.entity.User;
import com.darshan.projectmanagement.backend.exception.UserNotFoundException;
import com.darshan.projectmanagement.backend.repository.NotificationRepository;
import com.darshan.projectmanagement.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository) {

        this.notificationRepository =
                notificationRepository;

        this.userRepository =
                userRepository;
    }

    public void createNotification(
            User user,
            String message) {

        Notification notification =
                Notification.builder()
                        .message(message)
                        .read(false)
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .build();

        notificationRepository.save(notification);
    }

    public List<NotificationResponse>
    getMyNotifications(String email) {

        return notificationRepository
                .findByUserEmailOrderByCreatedAtDesc(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void markAsRead(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow();

        notification.setRead(true);

        notificationRepository.save(notification);
    }

    public void markAllAsRead(
            String email) {

        List<Notification> notifications =
                notificationRepository
                        .findByUserEmailOrderByCreatedAtDesc(
                                email);

        notifications.forEach(
                notification ->
                        notification.setRead(true));

        notificationRepository.saveAll(
                notifications);
    }

    private NotificationResponse
    mapToResponse(Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .read(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }

}