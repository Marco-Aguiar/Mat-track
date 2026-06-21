package com.mattrack.trainingtechnique.dto;

import jakarta.validation.constraints.Size;

public record UpdateTrainingTechniqueNoteRequest(

        @Size(max = 3000)
        String note
) {
}