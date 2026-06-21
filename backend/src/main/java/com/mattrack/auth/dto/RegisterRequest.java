package com.mattrack.auth.dto;

import com.mattrack.sport.SportType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record RegisterRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotBlank String password,
        String belt,
        BigDecimal weight,
        String academy,
        SportType primarySport
) {
}
