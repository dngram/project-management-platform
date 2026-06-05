package com.darshan.projectmanagement.backend.controller;

import com.darshan.projectmanagement.backend.dto.LoginRequest;
import com.darshan.projectmanagement.backend.dto.LoginResponse;
import com.darshan.projectmanagement.backend.dto.RegisterRequest;
import com.darshan.projectmanagement.backend.security.JwtService;
import com.darshan.projectmanagement.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService, JwtService jwtService) {

        this.authService = authService;
        this.jwtService = jwtService;
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

    @GetMapping("/test")
    public String testToken(
            @RequestParam String token) {

        return jwtService.extractEmail(token);
    }
}