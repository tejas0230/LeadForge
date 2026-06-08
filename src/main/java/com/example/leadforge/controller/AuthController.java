package com.example.leadforge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.leadforge.dto.LoginRequestDTO;
import com.example.leadforge.dto.LoginResponseDTO;
import com.example.leadforge.dto.RefreshTokenRequestDTO;
import com.example.leadforge.dto.UserRequestDTO;
import com.example.leadforge.service.AuthService;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDTO registerRequestDTO   ) {
        authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshTokenRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }
}
