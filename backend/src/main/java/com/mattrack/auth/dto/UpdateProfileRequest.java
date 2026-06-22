package com.mattrack.auth.dto;

import com.mattrack.sport.SportType;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProfileRequest(
        @Size(min = 1, max = 100) String name,
        @Size(max = 50) String belt,
        @Size(max = 100) String academy,
        BigDecimal weight,
        SportType primarySport
) {
}
