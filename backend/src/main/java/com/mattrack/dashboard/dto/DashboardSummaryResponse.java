package com.mattrack.dashboard.dto;

import java.math.BigDecimal;

public record DashboardSummaryResponse(
        long totalTrainings,
        long trainingsThisMonth,
        long totalTrainingMinutes,
        double totalTrainingHours,
        long totalRounds,
        double averageIntensity,
        BigDecimal currentWeightKg
) {
}