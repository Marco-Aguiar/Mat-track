package com.mattrack.training;

import com.mattrack.sport.SportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TrainingRepository extends JpaRepository<Training, UUID> {

    Page<Training> findAllByUserEmailOrderByTrainingDateDesc(String email, Pageable pageable);

    Page<Training> findAllByUserEmailAndSportTypeOrderByTrainingDateDesc(String email, SportType sportType, Pageable pageable);

    List<Training> findAllByUserEmailAndTrainingDateBetweenOrderByTrainingDateAsc(
            String email,
            LocalDate startDate,
            LocalDate endDate
    );

    Optional<Training> findByIdAndUserEmail(UUID id, String email);

    long countByUserEmailAndTrainingDateBetween(
            String email,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("""
            SELECT t
            FROM Training t
            WHERE t.user.email = :email
              AND t.trainingDate BETWEEN :startDate AND :endDate
              AND (:sportType IS NULL OR t.sportType = :sportType)
            ORDER BY t.trainingDate ASC
            """)
    List<Training> findAllByUserEmailAndDateBetweenAndOptionalSportType(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT t
            FROM Training t
            WHERE t.user.email = :email
              AND t.trainingDate BETWEEN :startDate AND :endDate
              AND (:sportType IS NULL OR t.sportType = :sportType)
            ORDER BY t.trainingDate DESC
            """)
    Page<Training> findPageByUserEmailAndDateBetweenAndOptionalSportType(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("sportType") SportType sportType,
            Pageable pageable
    );

    @Query("""
            SELECT COUNT(t)
            FROM Training t
            WHERE t.user.email = :email
              AND (:sportType IS NULL OR t.sportType = :sportType)
            """)
    long countAllByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT COUNT(t)
            FROM Training t
            WHERE t.user.email = :email
              AND t.trainingDate BETWEEN :startDate AND :endDate
              AND (:sportType IS NULL OR t.sportType = :sportType)
            """)
    long countByUserEmailAndTrainingDateBetweenAndOptionalSportType(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT COALESCE(SUM(t.durationMinutes), 0)
            FROM Training t
            WHERE t.user.email = :email
              AND (:sportType IS NULL OR t.sportType = :sportType)
            """)
    Long sumDurationMinutesByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT COALESCE(SUM(t.rounds), 0)
            FROM Training t
            WHERE t.user.email = :email
              AND (:sportType IS NULL OR t.sportType = :sportType)
            """)
    Long sumRoundsByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT COALESCE(AVG(t.intensity), 0)
            FROM Training t
            WHERE t.user.email = :email
              AND (:sportType IS NULL OR t.sportType = :sportType)
            """)
    Double averageIntensityByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT COALESCE(SUM(t.distanceKm), 0)
            FROM Training t
            WHERE t.user.email = :email
              AND (:sportType IS NULL OR t.sportType = :sportType)
            """)
    java.math.BigDecimal sumDistanceKmByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT COALESCE(SUM(t.caloriesBurned), 0)
            FROM Training t
            WHERE t.user.email = :email
              AND (:sportType IS NULL OR t.sportType = :sportType)
            """)
    Long sumCaloriesBurnedByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT COUNT(t)
            FROM Training t
            WHERE t.user.email = :email
            """)
    long countAllByUserEmail(@Param("email") String email);

    @Query("""
            SELECT COALESCE(SUM(t.durationMinutes), 0)
            FROM Training t
            WHERE t.user.email = :email
            """)
    Long sumDurationMinutesByUserEmail(@Param("email") String email);

    @Query("""
            SELECT COALESCE(SUM(t.rounds), 0)
            FROM Training t
            WHERE t.user.email = :email
            """)
    Long sumRoundsByUserEmail(@Param("email") String email);

    @Query("""
            SELECT COALESCE(AVG(t.intensity), 0)
            FROM Training t
            WHERE t.user.email = :email
            """)
    Double averageIntensityByUserEmail(@Param("email") String email);
}
