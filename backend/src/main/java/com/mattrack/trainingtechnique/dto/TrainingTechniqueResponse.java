package com.mattrack.trainingtechnique.dto;

import com.mattrack.technique.TechniqueCategory;

import java.util.UUID;

public record TrainingTechniqueResponse(
        UUID techniqueId,
        String name,
        TechniqueCategory category,
        String description,
        String note
) {
}