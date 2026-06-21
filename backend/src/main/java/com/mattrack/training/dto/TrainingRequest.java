package com.mattrack.training.dto;

import com.mattrack.training.TrainingType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TrainingRequest(

        @NotNull
        @PastOrPresent
        LocalDate trainingDate,

        @NotNull
        TrainingType type,

        @Min(1)
        @Max(1440)
        Integer durationMinutes,

        @Min(0)
        @Max(100)
        Short rounds,

        @Min(1)
        @Max(5)
        Short intensity,

        @Size(max = 3000)
        String notes
) {
}