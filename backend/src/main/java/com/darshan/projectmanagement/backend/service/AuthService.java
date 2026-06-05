package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.LoginRequest;
import com.darshan.projectmanagement.backend.dto.LoginResponse;
import com.darshan.projectmanagement.backend.dto.RegisterRequest;
import com.darshan.projectmanagement.backend.entity.User;
import com.darshan.projectmanagement.backend.exception.InvalidCredentialsException;
import com.darshan.projectmanagement.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        return LoginResponse.builder()
                .token("JWT_COMING_NEXT")
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}