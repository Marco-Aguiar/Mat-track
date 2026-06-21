package com.mattrack.training.dto;

import com.mattrack.training.TrainingType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TrainingResponse(
        UUID id,
        LocalDate trainingDate,
        TrainingType type,
        Integer durationMinutes,
        Short rounds,
        Short intensity,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}