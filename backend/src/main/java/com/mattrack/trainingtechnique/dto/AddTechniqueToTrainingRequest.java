package com.mattrack.trainingtechnique.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddTechniqueToTrainingRequest(

        @NotNull
        UUID techniqueId,

        @Size(max = 3000)
        String note
) {
}