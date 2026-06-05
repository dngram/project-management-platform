package com.darshan.projectmanagement.backend.controller;

import com.darshan.projectmanagement.backend.dto.*;
import com.darshan.projectmanagement.backend.security.JwtService;
import com.darshan.projectmanagement.backend.service.AuthService;
import com.darshan.projectmanagement.backend.service.ForgotPasswordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final ForgotPasswordService forgotPasswordService;

    public AuthController(
            AuthService authService, JwtService jwtService, ForgotPasswordService forgotPasswordService) {

        this.authService = authService;
        this.jwtService = jwtService;
        this.forgotPasswordService = forgotPasswordService;
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

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @Valid
            @RequestBody
            ForgotPasswordRequest request) {

        forgotPasswordService.forgotPassword(
                request.getEmail());

        return "Password reset email sent";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid
            @RequestBody
            ResetPasswordRequest request) {

        forgotPasswordService.resetPassword(
                request);

        return "Password reset successful";
    }
}