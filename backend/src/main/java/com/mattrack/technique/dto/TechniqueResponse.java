package com.mattrack.technique.dto;

import com.mattrack.sport.SportType;
import com.mattrack.technique.TechniqueCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record TechniqueResponse(
        UUID id,
        String name,
        SportType sportType,
        TechniqueCategory category,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
