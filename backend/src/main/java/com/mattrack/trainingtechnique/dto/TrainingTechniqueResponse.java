package com.mattrack.trainingtechnique.dto;

import com.mattrack.sport.SportType;
import com.mattrack.technique.TechniqueCategory;

import java.math.BigDecimal;
import java.util.UUID;

public record TrainingTechniqueResponse(
        UUID techniqueId,
        String name,
        SportType sportType,
        TechniqueCategory category,
        String description,
        Short sets,
        Short reps,
        BigDecimal loadKg,
        BigDecimal distanceKm,
        Integer durationSeconds,
        String note
) {
}
