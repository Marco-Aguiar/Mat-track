package com.mattrack.trainingtechnique;

import com.mattrack.config.CacheConfig;
import com.mattrack.technique.Technique;
import com.mattrack.technique.TechniqueNotFoundException;
import com.mattrack.technique.TechniqueRepository;
import com.mattrack.training.Training;
import com.mattrack.training.TrainingNotFoundException;
import com.mattrack.training.TrainingRepository;
import com.mattrack.trainingtechnique.dto.AddTechniqueToTrainingRequest;
import com.mattrack.trainingtechnique.dto.TrainingTechniqueResponse;
import com.mattrack.trainingtechnique.dto.UpdateTrainingTechniqueNoteRequest;
import com.mattrack.user.User;
import com.mattrack.user.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingTechniqueService {

    private final TrainingRepository trainingRepository;
    private final TechniqueRepository techniqueRepository;
    private final TrainingTechniqueRepository trainingTechniqueRepository;
    private final UserRepository userRepository;

    public TrainingTechniqueService(
            TrainingRepository trainingRepository,
            TechniqueRepository techniqueRepository,
            TrainingTechniqueRepository trainingTechniqueRepository,
            UserRepository userRepository
    ) {
        this.trainingRepository = trainingRepository;
        this.techniqueRepository = techniqueRepository;
        this.trainingTechniqueRepository = trainingTechniqueRepository;
        this.userRepository = userRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheConfig.DASHBOARD_TECHNIQUES, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_CATEGORIES, allEntries = true)
    })
    @Transactional
    public TrainingTechniqueResponse addTechnique(
            String email,
            UUID trainingId,
            AddTechniqueToTrainingRequest request
    ) {
        Training training = findOwnedTraining(email, trainingId);
        User user = findUser(email);

        Technique technique = findVisibleTechnique(user, request.techniqueId());

        validateSameSportType(training, technique);

        boolean alreadyLinked = trainingTechniqueRepository
                .existsByTraining_IdAndTechnique_Id(trainingId, request.techniqueId());

        if (alreadyLinked) {
            throw new IllegalArgumentException("Technique already linked to this training");
        }

        TrainingTechnique trainingTechnique = new TrainingTechnique(training, technique);
        applyFields(trainingTechnique, request.sets(), request.reps(), request.loadKg(), request.distanceKm(), request.durationSeconds(), request.note());

        return toResponse(trainingTechniqueRepository.save(trainingTechnique));
    }

    @Transactional(readOnly = true)
    public List<TrainingTechniqueResponse> findTechniquesByTraining(
            String email,
            UUID trainingId
    ) {
        findOwnedTraining(email, trainingId);

        return trainingTechniqueRepository
                .findAllByTraining_IdOrderByTechnique_NameAsc(trainingId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TrainingTechniqueResponse updateNote(
            String email,
            UUID trainingId,
            UUID techniqueId,
            UpdateTrainingTechniqueNoteRequest request
    ) {
        findOwnedTraining(email, trainingId);

        TrainingTechnique trainingTechnique = trainingTechniqueRepository
                .findByTraining_IdAndTechnique_Id(trainingId, techniqueId)
                .orElseThrow(TrainingTechniqueNotFoundException::new);

        applyFields(trainingTechnique, request.sets(), request.reps(), request.loadKg(), request.distanceKm(), request.durationSeconds(), request.note());

        return toResponse(trainingTechniqueRepository.save(trainingTechnique));
    }

    @Caching(evict = {
            @CacheEvict(value = CacheConfig.DASHBOARD_TECHNIQUES, allEntries = true),
            @CacheEvict(value = CacheConfig.DASHBOARD_CATEGORIES, allEntries = true)
    })
    @Transactional
    public void removeTechnique(
            String email,
            UUID trainingId,
            UUID techniqueId
    ) {
        findOwnedTraining(email, trainingId);

        TrainingTechnique trainingTechnique = trainingTechniqueRepository
                .findByTraining_IdAndTechnique_Id(trainingId, techniqueId)
                .orElseThrow(TrainingTechniqueNotFoundException::new);

        trainingTechniqueRepository.delete(trainingTechnique);
    }

    private Training findOwnedTraining(String email, UUID trainingId) {
        return trainingRepository.findByIdAndUserEmail(trainingId, email)
                .orElseThrow(TrainingNotFoundException::new);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Technique findVisibleTechnique(User user, UUID techniqueId) {
        Technique technique = techniqueRepository.findById(techniqueId)
                .orElseThrow(TechniqueNotFoundException::new);
        if (technique.getCreatedBy() != null && !technique.getCreatedBy().getId().equals(user.getId())) {
            throw new TechniqueNotFoundException();
        }
        return technique;
    }

    private void validateSameSportType(Training training, Technique technique) {
        if (training.getSportType() != technique.getSportType()) {
            throw new IllegalArgumentException("Technique sport type must match training sport type");
        }
    }

    private void applyFields(
            TrainingTechnique trainingTechnique,
            Short sets,
            Short reps,
            java.math.BigDecimal loadKg,
            java.math.BigDecimal distanceKm,
            Integer durationSeconds,
            String note
    ) {
        trainingTechnique.setSets(sets);
        trainingTechnique.setReps(reps);
        trainingTechnique.setLoadKg(loadKg);
        trainingTechnique.setDistanceKm(distanceKm);
        trainingTechnique.setDurationSeconds(durationSeconds);
        trainingTechnique.setNote(note);
    }

    private TrainingTechniqueResponse toResponse(TrainingTechnique trainingTechnique) {
        Technique technique = trainingTechnique.getTechnique();

        return new TrainingTechniqueResponse(
                technique.getId(),
                technique.getName(),
                technique.getSportType(),
                technique.getCategory(),
                technique.getDescription(),
                trainingTechnique.getSets(),
                trainingTechnique.getReps(),
                trainingTechnique.getLoadKg(),
                trainingTechnique.getDistanceKm(),
                trainingTechnique.getDurationSeconds(),
                trainingTechnique.getNote()
        );
    }
}
