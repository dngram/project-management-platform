package com.darshan.projectmanagement.backend.controller;

import com.darshan.projectmanagement.backend.dto.LoginRequest;
import com.darshan.projectmanagement.backend.dto.LoginResponse;
import com.darshan.projectmanagement.backend.dto.RegisterRequest;
import com.darshan.projectmanagement.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}