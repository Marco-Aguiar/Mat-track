package com.mattrack.trainingtechnique;

import com.mattrack.technique.Technique;
import com.mattrack.technique.TechniqueNotFoundException;
import com.mattrack.technique.TechniqueRepository;
import com.mattrack.training.Training;
import com.mattrack.training.TrainingNotFoundException;
import com.mattrack.training.TrainingRepository;
import com.mattrack.trainingtechnique.dto.AddTechniqueToTrainingRequest;
import com.mattrack.trainingtechnique.dto.TrainingTechniqueResponse;
import com.mattrack.trainingtechnique.dto.UpdateTrainingTechniqueNoteRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingTechniqueService {

    private final TrainingRepository trainingRepository;
    private final TechniqueRepository techniqueRepository;
    private final TrainingTechniqueRepository trainingTechniqueRepository;

    public TrainingTechniqueService(
            TrainingRepository trainingRepository,
            TechniqueRepository techniqueRepository,
            TrainingTechniqueRepository trainingTechniqueRepository
    ) {
        this.trainingRepository = trainingRepository;
        this.techniqueRepository = techniqueRepository;
        this.trainingTechniqueRepository = trainingTechniqueRepository;
    }

    @Transactional
    public TrainingTechniqueResponse addTechnique(
            String email,
            UUID trainingId,
            AddTechniqueToTrainingRequest request
    ) {
        Training training = findOwnedTraining(email, trainingId);

        Technique technique = techniqueRepository.findById(request.techniqueId())
                .orElseThrow(TechniqueNotFoundException::new);

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
