package com.example.flights_1p_parcial.auth.application;

import com.example.flights_1p_parcial.auth.domain.AuthService;
import com.example.flights_1p_parcial.auth.dto.LoginRequestDto;
import com.example.flights_1p_parcial.auth.dto.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}