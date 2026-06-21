package com.mattrack.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WeightProgressResponse(
        LocalDate measuredAt,
        BigDecimal weightKg
) {
}