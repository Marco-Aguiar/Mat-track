package com.mattrack.weighthistory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record WeightHistoryResponse(
        UUID id,
        BigDecimal weightKg,
        LocalDate measuredAt,
        String note,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}