package com.mattrack.technique.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TechniqueProgressionResponse(
        LocalDate trainingDate,
        UUID trainingId,
        Short sets,
        Short reps,
        BigDecimal loadKg,
        BigDecimal distanceKm,
        Integer durationSeconds,
        String note
) {
}
