package com.mattrack.trainingtechnique.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record AddTechniqueToTrainingRequest(

        @NotNull
        UUID techniqueId,

        @Min(0)
        @Max(100)
        Short sets,

        @Min(0)
        @Max(1000)
        Short reps,

        @DecimalMin("0.0")
        @Digits(integer = 4, fraction = 2)
        BigDecimal loadKg,

        @DecimalMin("0.0")
        @Digits(integer = 4, fraction = 2)
        BigDecimal distanceKm,

        @Min(0)
        @Max(86400)
        Integer durationSeconds,

        @Size(max = 3000)
        String note
) {
}
