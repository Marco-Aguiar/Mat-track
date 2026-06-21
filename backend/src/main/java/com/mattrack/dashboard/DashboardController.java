package com.mattrack.dashboard;

import com.mattrack.dashboard.dto.*;
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
    public DashboardSummaryResponse getSummary(Authentication authentication) {
        return dashboardService.getSummary(authentication.getName());
    }

    @GetMapping("/weekly-trainings")
    public List<WeeklyTrainingResponse> getWeeklyTrainings(Authentication authentication) {
        return dashboardService.getWeeklyTrainings(authentication.getName());
    }

    @GetMapping("/most-trained-techniques")
    public List<MostTrainedTechniqueResponse> getMostTrainedTechniques(Authentication authentication) {
        return dashboardService.getMostTrainedTechniques(authentication.getName());
    }

    @GetMapping("/techniques-by-category")
    public List<TechniqueCategorySummaryResponse> getTechniquesByCategory(Authentication authentication) {
        return dashboardService.getTechniquesByCategory(authentication.getName());
    }

    @GetMapping("/weight-progress")
    public List<WeightProgressResponse> getWeightProgress(Authentication authentication) {
        return dashboardService.getWeightProgress(authentication.getName());
    }
}