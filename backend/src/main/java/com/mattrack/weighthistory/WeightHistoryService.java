package com.mattrack.weighthistory;

import com.mattrack.user.User;
import com.mattrack.user.UserRepository;
import com.mattrack.weighthistory.dto.WeightHistoryRequest;
import com.mattrack.weighthistory.dto.WeightHistoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WeightHistoryService {

    private final WeightHistoryRepository weightHistoryRepository;
    private final UserRepository userRepository;

    public WeightHistoryService(
            WeightHistoryRepository weightHistoryRepository,
            UserRepository userRepository
    ) {
        this.weightHistoryRepository = weightHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public WeightHistoryResponse create(String email, WeightHistoryRequest request) {
        User user = findUser(email);

        weightHistoryRepository.findByUserEmailAndMeasuredAt(email, request.measuredAt())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Weight already registered for this date");
                });

        WeightHistory weightHistory = new WeightHistory();
        weightHistory.setUser(user);
        updateFields(weightHistory, request);

        WeightHistory saved = weightHistoryRepository.save(weightHistory);

        syncCurrentWeight(user);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<WeightHistoryResponse> findAll(String email) {
        return weightHistoryRepository
                .findAllByUserEmailOrderByMeasuredAtAsc(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public WeightHistoryResponse findById(String email, UUID id) {
        return toResponse(findOwnedWeightHistory(email, id));
    }

    @Transactional(readOnly = true)
    public WeightHistoryResponse findLatest(String email) {
        WeightHistory latest = weightHistoryRepository
                .findTopByUserEmailOrderByMeasuredAtDesc(email)
                .orElseThrow(WeightHistoryNotFoundException::new);

        return toResponse(latest);
    }

    @Transactional
    public WeightHistoryResponse update(
            String email,
            UUID id,
            WeightHistoryRequest request
    ) {
        User user = findUser(email);
        WeightHistory weightHistory = findOwnedWeightHistory(email, id);

        weightHistoryRepository.findByUserEmailAndMeasuredAt(email, request.measuredAt())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Weight already registered for this date");
                });

        updateFields(weightHistory, request);

        WeightHistory saved = weightHistoryRepository.save(weightHistory);

        syncCurrentWeight(user);

        return toResponse(saved);
    }

    @Transactional
    public void delete(String email, UUID id) {
        User user = findUser(email);
        WeightHistory weightHistory = findOwnedWeightHistory(email, id);

        weightHistoryRepository.delete(weightHistory);
        weightHistoryRepository.flush();

        syncCurrentWeight(user);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private WeightHistory findOwnedWeightHistory(String email, UUID id) {
        return weightHistoryRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(WeightHistoryNotFoundException::new);
    }

    private void updateFields(
            WeightHistory weightHistory,
            WeightHistoryRequest request
    ) {
        weightHistory.setWeightKg(request.weightKg());
        weightHistory.setMeasuredAt(request.measuredAt());
        weightHistory.setNote(request.note());
    }

    private void syncCurrentWeight(User user) {
        weightHistoryRepository
                .findTopByUserEmailOrderByMeasuredAtDesc(user.getEmail())
                .ifPresentOrElse(
                        latest -> user.setWeight(latest.getWeightKg()),
                        () -> user.setWeight(null)
                );

        userRepository.save(user);
    }

    private WeightHistoryResponse toResponse(WeightHistory weightHistory) {
        return new WeightHistoryResponse(
                weightHistory.getId(),
                weightHistory.getWeightKg(),
                weightHistory.getMeasuredAt(),
                weightHistory.getNote(),
                weightHistory.getCreatedAt(),
                weightHistory.getUpdatedAt()
        );
    }
}