package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.ResetPasswordRequest;
import com.darshan.projectmanagement.backend.entity.PasswordResetToken;
import com.darshan.projectmanagement.backend.entity.User;
import com.darshan.projectmanagement.backend.repository.PasswordResetTokenRepository;
import com.darshan.projectmanagement.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordService(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken =
                PasswordResetToken.builder()
                        .token(token)
                        .expiryDate(
                                LocalDateTime.now().plusMinutes(30))
                        .user(user)
                        .build();

        tokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(
                email,
                token);
    }

    public void resetPassword(
            ResetPasswordRequest request) {

        PasswordResetToken token =
                tokenRepository.findByToken(
                                request.getToken())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid token"));

        if (token.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Token expired");
        }

        User user = token.getUser();

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);

        tokenRepository.delete(token);
    }
}