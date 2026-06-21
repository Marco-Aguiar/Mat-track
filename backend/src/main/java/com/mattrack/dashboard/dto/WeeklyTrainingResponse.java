package com.mattrack.dashboard.dto;

import java.time.LocalDate;

public record WeeklyTrainingResponse(
        LocalDate weekStart,
        LocalDate weekEnd,
        long trainingCount,
        long totalMinutes
) {
}