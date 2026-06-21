package com.mattrack.technique.dto;

import com.mattrack.technique.TechniqueCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TechniqueRequest(

        @NotBlank
        @Size(max = 120)
        String name,

        @NotNull
        TechniqueCategory category,

        @Size(max = 3000)
        String description
) {
}