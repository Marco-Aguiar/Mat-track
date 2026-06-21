package com.mattrack.dashboard.dto;

import com.mattrack.technique.TechniqueCategory;

public record TechniqueCategorySummaryResponse(
        TechniqueCategory category,
        long count
) {
}