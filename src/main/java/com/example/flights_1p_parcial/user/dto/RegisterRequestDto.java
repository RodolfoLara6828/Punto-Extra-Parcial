package com.example.flights_1p_parcial.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*", message = "must contain at least one uppercase letter")
    private String firstName;

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*", message = "must contain at least one uppercase letter")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "must have at least 8 characters, one letter and one number")
    private String password;
}