package com.mattrack.auth.dto;

import com.mattrack.sport.SportType;
import com.mattrack.user.Role;

import java.math.BigDecimal;
import java.util.UUID;

public record MeResponse(
        UUID id,
        String name,
        String email,
        String belt,
        BigDecimal weight,
        String academy,
        SportType primarySport,
        Role role
) {
}
