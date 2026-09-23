package com.example.flights_1p_parcial.user.application;

import com.example.flights_1p_parcial.user.domain.UserService;
import com.example.flights_1p_parcial.user.dto.RegisterRequestDto;
import com.example.flights_1p_parcial.user.dto.RegisterResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
    }
}