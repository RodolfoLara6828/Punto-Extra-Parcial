package com.example.flights_1p_parcial.auth.domain;

import com.example.flights_1p_parcial.auth.components.JwtService;
import com.example.flights_1p_parcial.auth.dto.LoginRequestDto;
import com.example.flights_1p_parcial.auth.dto.TokenResponse;
import com.example.flights_1p_parcial.user.domain.User;
import com.example.flights_1p_parcial.user.infrastructure.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponse login(LoginRequestDto dto) {
        // Caso 1: email desconocido
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Unknown email"));

        // Caso 2: contraseña incorrecta (authenticate lanza BadCredentialsException)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        return new TokenResponse(jwtService.generateToken(user));
    }
}