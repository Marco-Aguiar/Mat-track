package com.mattrack.weighthistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeightHistoryRepository extends JpaRepository<WeightHistory, UUID> {

    List<WeightHistory> findAllByUserEmailOrderByMeasuredAtAsc(String email);

    Optional<WeightHistory> findByIdAndUserEmail(UUID id, String email);

    Optional<WeightHistory> findTopByUserEmailOrderByMeasuredAtDesc(String email);

    Optional<WeightHistory> findByUserEmailAndMeasuredAt(String email, LocalDate measuredAt);
}