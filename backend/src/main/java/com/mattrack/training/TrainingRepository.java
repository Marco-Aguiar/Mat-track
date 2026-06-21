package com.mattrack.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {

    List<Training> findAllByUserEmailOrderByTrainingDateDesc(String email);

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