package com.mattrack.training;

import com.mattrack.common.PageResponse;
import com.mattrack.config.CacheConfig;
import com.mattrack.sport.SportType;
import com.mattrack.training.dto.TrainingRequest;
import com.mattrack.training.dto.TrainingResponse;
import com.mattrack.user.User;
import com.mattrack.user.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    public TrainingService(
            TrainingRepository trainingRepository,
            UserRepository userRepository
    ) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheConfig.DASHBOARD_SUMMARY, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_WEEKLY, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_TECHNIQUES, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_CATEGORIES, allEntries = true)
    })
    @Transactional
    public TrainingResponse create(String email, TrainingRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Training training = new Training();
        training.setUser(user);
        updateFields(training, request);

        return toResponse(trainingRepository.save(training));
    }

    @Transactional(readOnly = true)
    public PageResponse<TrainingResponse> findAll(
            String email,
            SportType sportType,
            LocalDate startDate,
            LocalDate endDate,
            int page,
            int size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by("trainingDate").descending());

        if (startDate != null || endDate != null) {
            LocalDate from = startDate != null ? startDate : LocalDate.of(2000, 1, 1);
            LocalDate to   = endDate   != null ? endDate   : LocalDate.now();
            return PageResponse.from(
                    trainingRepository.findPageByUserEmailAndDateBetweenAndOptionalSportType(email, from, to, sportType, pageable)
                            .map(this::toResponse)
            );
        }

        var trainings = sportType == null
                ? trainingRepository.findAllByUserEmailOrderByTrainingDateDesc(email, pageable)
                : trainingRepository.findAllByUserEmailAndSportTypeOrderByTrainingDateDesc(email, sportType, pageable);

        return PageResponse.from(trainings.map(this::toResponse));
    }

    @Transactional(readOnly = true)
    public TrainingResponse findById(String email, UUID id) {
        return toResponse(findOwnedTraining(email, id));
    }

    @Caching(evict = {
            @CacheEvict(value = CacheConfig.DASHBOARD_SUMMARY, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_WEEKLY, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_TECHNIQUES, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_CATEGORIES, allEntries = true)
    })
    @Transactional
    public TrainingResponse update(
            String email,
            UUID id,
            TrainingRequest request
    ) {
        Training training = findOwnedTraining(email, id);
        updateFields(training, request);

        return toResponse(trainingRepository.save(training));
    }

    @Caching(evict = {
            @CacheEvict(value = CacheConfig.DASHBOARD_SUMMARY, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_WEEKLY, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_TECHNIQUES, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_CATEGORIES, allEntries = true)
    })
    @Transactional
    public void delete(String email, UUID id) {
        Training training = findOwnedTraining(email, id);
        trainingRepository.delete(training);
    }

    private Training findOwnedTraining(String email, UUID id) {
        return trainingRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(TrainingNotFoundException::new);
    }

    private void updateFields(Training training, TrainingRequest request) {
        SportType resolvedSportType = resolveSportType(request);

        training.setTrainingDate(request.trainingDate());
        training.setSportType(resolvedSportType);
        training.setType(request.type());
        training.setDurationMinutes(request.durationMinutes());
        training.setRounds(request.rounds());
        training.setIntensity(request.intensity());
        training.setDistanceKm(request.distanceKm());
        training.setCaloriesBurned(request.caloriesBurned());
        training.setNotes(request.notes());
    }

    private SportType resolveSportType(TrainingRequest request) {
        SportType resolvedSportType = request.sportType() == null
                ? request.type().getSportType()
                : request.sportType();

        if (!request.type().belongsTo(resolvedSportType)) {
            throw new IllegalArgumentException("Training type " + request.type() + " does not belong to sport type " + resolvedSportType);
        }

        return resolvedSportType;
    }

    private TrainingResponse toResponse(Training training) {
        return new TrainingResponse(
                training.getId(),
                training.getTrainingDate(),
                training.getSportType(),
                training.getType(),
                training.getDurationMinutes(),
                training.getRounds(),
                training.getIntensity(),
                training.getDistanceKm(),
                training.getCaloriesBurned(),
                training.getNotes(),
                training.getCreatedAt(),
                training.getUpdatedAt()
        );
    }
}
