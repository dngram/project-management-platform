package com.darshan.projectmanagement.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(
            JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(
            String email,
            String token) {

        String resetLink =
                "http://localhost:4200/reset-password?token="
                        + token;

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);

        message.setSubject(
                "Password Reset Request");

        message.setText(
                "Click the link below to reset your password:\n\n"
                        + resetLink);

        message.setFrom(
                "saasprojectmanagement@gmail.com"
        );

        mailSender.send(message);
    }
}