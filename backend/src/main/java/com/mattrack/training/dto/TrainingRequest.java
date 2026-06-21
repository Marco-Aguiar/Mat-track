package com.mattrack.training.dto;

import com.mattrack.sport.SportType;
import com.mattrack.training.TrainingType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TrainingRequest(

        @NotNull
        @PastOrPresent
        LocalDate trainingDate,

        SportType sportType,

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

        @DecimalMin("0.0")
        @Digits(integer = 4, fraction = 2)
        BigDecimal distanceKm,

        @Min(0)
        @Max(20000)
        Integer caloriesBurned,

        @Size(max = 3000)
        String notes
) {
}
