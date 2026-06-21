package com.mattrack.dashboard.dto;

import com.mattrack.sport.SportType;
import com.mattrack.technique.TechniqueCategory;

import java.util.UUID;

public record MostTrainedTechniqueResponse(
        UUID techniqueId,
        String name,
        SportType sportType,
        TechniqueCategory category,
        long count
) {
}
