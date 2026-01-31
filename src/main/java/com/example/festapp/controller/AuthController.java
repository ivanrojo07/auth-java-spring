package com.example.festapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.festapp.dto.LoginRequest;
import com.example.festapp.dto.RegisterRequest;
import com.example.festapp.service.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postMethodName(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.ok("Usuario registrado.");
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        authService.login(request);
        return ResponseEntity.ok(authService.login(request));
    }
    
}
