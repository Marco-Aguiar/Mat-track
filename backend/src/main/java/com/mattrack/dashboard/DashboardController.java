package com.mattrack.dashboard;

import com.mattrack.dashboard.dto.*;
import com.mattrack.sport.SportType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary(
            Authentication authentication,
            @RequestParam(required = false) SportType sportType
    ) {
        return dashboardService.getSummary(authentication.getName(), sportType);
    }

    @GetMapping("/weekly-trainings")
    public List<WeeklyTrainingResponse> getWeeklyTrainings(
            Authentication authentication,
            @RequestParam(required = false) SportType sportType
    ) {
        return dashboardService.getWeeklyTrainings(authentication.getName(), sportType);
    }

    @GetMapping("/most-trained-techniques")
    public List<MostTrainedTechniqueResponse> getMostTrainedTechniques(
            Authentication authentication,
            @RequestParam(required = false) SportType sportType
    ) {
        return dashboardService.getMostTrainedTechniques(authentication.getName(), sportType);
    }

    @GetMapping("/techniques-by-category")
    public List<TechniqueCategorySummaryResponse> getTechniquesByCategory(
            Authentication authentication,
            @RequestParam(required = false) SportType sportType
    ) {
        return dashboardService.getTechniquesByCategory(authentication.getName(), sportType);
    }

    @GetMapping("/weight-progress")
    public List<WeightProgressResponse> getWeightProgress(Authentication authentication) {
        return dashboardService.getWeightProgress(authentication.getName());
    }
}
