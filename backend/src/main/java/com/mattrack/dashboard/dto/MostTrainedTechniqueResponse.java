package com.mattrack.dashboard.dto;

import com.mattrack.technique.TechniqueCategory;

import java.util.UUID;

public record MostTrainedTechniqueResponse(
        UUID techniqueId,
        String name,
        TechniqueCategory category,
        long count
) {
}