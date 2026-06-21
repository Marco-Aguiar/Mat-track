package com.mattrack.training.dto;

import com.mattrack.sport.SportType;
import com.mattrack.training.TrainingType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TrainingResponse(
        UUID id,
        LocalDate trainingDate,
        SportType sportType,
        TrainingType type,
        Integer durationMinutes,
        Short rounds,
        Short intensity,
        BigDecimal distanceKm,
        Integer caloriesBurned,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
