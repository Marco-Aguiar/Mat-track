package com.mattrack.dashboard;

import com.mattrack.dashboard.dto.*;
import com.mattrack.sport.SportType;
import com.mattrack.technique.TechniqueCategory;
import com.mattrack.training.Training;
import com.mattrack.training.TrainingRepository;
import com.mattrack.trainingtechnique.TrainingTechniqueRepository;
import com.mattrack.user.User;
import com.mattrack.user.UserRepository;
import com.mattrack.weighthistory.WeightHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class DashboardService {

    private final TrainingRepository trainingRepository;
    private final TrainingTechniqueRepository trainingTechniqueRepository;
    private final WeightHistoryRepository weightHistoryRepository;
    private final UserRepository userRepository;

    public DashboardService(
            TrainingRepository trainingRepository,
            TrainingTechniqueRepository trainingTechniqueRepository,
            WeightHistoryRepository weightHistoryRepository,
            UserRepository userRepository
    ) {
        this.trainingRepository = trainingRepository;
        this.trainingTechniqueRepository = trainingTechniqueRepository;
        this.weightHistoryRepository = weightHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary(String email, SportType sportType) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = today.with(TemporalAdjusters.lastDayOfMonth());

        long totalTrainings = trainingRepository.countAllByUserEmailAndOptionalSportType(email, sportType);
        long trainingsThisMonth = trainingRepository.countByUserEmailAndTrainingDateBetweenAndOptionalSportType(
                email,
                monthStart,
                monthEnd,
                sportType
        );

        long totalMinutes = safeLong(trainingRepository.sumDurationMinutesByUserEmailAndOptionalSportType(email, sportType));
        long totalRounds = safeLong(trainingRepository.sumRoundsByUserEmailAndOptionalSportType(email, sportType));
        double averageIntensity = roundOneDecimal(trainingRepository.averageIntensityByUserEmailAndOptionalSportType(email, sportType));
        double totalHours = roundOneDecimal(totalMinutes / 60.0);
        BigDecimal totalDistanceKm = safeBigDecimal(trainingRepository.sumDistanceKmByUserEmailAndOptionalSportType(email, sportType));
        long totalCaloriesBurned = safeLong(trainingRepository.sumCaloriesBurnedByUserEmailAndOptionalSportType(email, sportType));

        return new DashboardSummaryResponse(
                totalTrainings,
                trainingsThisMonth,
                totalMinutes,
                totalHours,
                totalRounds,
                averageIntensity,
                totalDistanceKm,
                totalCaloriesBurned,
                user.getWeight()
        );
    }

    @Transactional(readOnly = true)
    public List<WeeklyTrainingResponse> getWeeklyTrainings(String email, SportType sportType) {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusWeeks(7).with(DayOfWeek.MONDAY);
        LocalDate end = today.with(DayOfWeek.SUNDAY);

        List<Training> trainings = trainingRepository
                .findAllByUserEmailAndDateBetweenAndOptionalSportType(
                        email,
                        start,
                        end,
                        sportType
                );

        Map<LocalDate, WeeklyAccumulator> groupedByWeek = new LinkedHashMap<>();

        LocalDate cursor = start;
        while (!cursor.isAfter(end)) {
            groupedByWeek.put(cursor, new WeeklyAccumulator());
            cursor = cursor.plusWeeks(1);
        }

        for (Training training : trainings) {
            LocalDate weekStart = training.getTrainingDate().with(DayOfWeek.MONDAY);

            WeeklyAccumulator accumulator = groupedByWeek.get(weekStart);

            if (accumulator != null) {
                accumulator.trainingCount++;
                accumulator.totalMinutes += training.getDurationMinutes() == null
                        ? 0
                        : training.getDurationMinutes();
            }
        }

        return groupedByWeek.entrySet()
                .stream()
                .map(entry -> new WeeklyTrainingResponse(
                        entry.getKey(),
                        entry.getKey().plusDays(6),
                        entry.getValue().trainingCount,
                        entry.getValue().totalMinutes
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MostTrainedTechniqueResponse> getMostTrainedTechniques(String email, SportType sportType) {
        return trainingTechniqueRepository
                .findMostTrainedTechniquesByUserEmailAndOptionalSportType(email, sportType)
                .stream()
                .map(row -> new MostTrainedTechniqueResponse(
                        (UUID) row[0],
                        (String) row[1],
                        (SportType) row[2],
                        (TechniqueCategory) row[3],
                        ((Number) row[4]).longValue()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TechniqueCategorySummaryResponse> getTechniquesByCategory(String email, SportType sportType) {
        return trainingTechniqueRepository
                .countTechniquesByCategoryByUserEmailAndOptionalSportType(email, sportType)
                .stream()
                .map(row -> new TechniqueCategorySummaryResponse(
                        (SportType) row[0],
                        (TechniqueCategory) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<WeightProgressResponse> getWeightProgress(String email) {
        return weightHistoryRepository
                .findAllByUserEmailOrderByMeasuredAtAsc(email)
                .stream()
                .map(weightHistory -> new WeightProgressResponse(
                        weightHistory.getMeasuredAt(),
                        weightHistory.getWeightKg()
                ))
                .toList();
    }

    private long safeLong(Long value) {
        return value == null ? 0L : value;
    }

    private BigDecimal safeBigDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private double roundOneDecimal(Double value) {
        if (value == null) {
            return 0.0;
        }

        return Math.round(value * 10.0) / 10.0;
    }

    private static class WeeklyAccumulator {
        private long trainingCount;
        private long totalMinutes;
    }
}
