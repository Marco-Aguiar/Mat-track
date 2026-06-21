package com.mattrack.dashboard.dto;

import com.mattrack.sport.SportType;
import com.mattrack.technique.TechniqueCategory;

public record TechniqueCategorySummaryResponse(
        SportType sportType,
        TechniqueCategory category,
        long count
) {
}
