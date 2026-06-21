package com.mattrack.weighthistory.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WeightHistoryRequest(

        @NotNull
        @DecimalMin("20.00")
        @DecimalMax("300.00")
        BigDecimal weightKg,

        @NotNull
        @PastOrPresent
        LocalDate measuredAt,

        @Size(max = 1000)
        String note
) {
}