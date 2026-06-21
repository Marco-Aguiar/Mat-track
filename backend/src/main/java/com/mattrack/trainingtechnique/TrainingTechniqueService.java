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

        boolean alreadyLinked = trainingTechniqueRepository
                .existsByTraining_IdAndTechnique_Id(trainingId, request.techniqueId());

        if (alreadyLinked) {
            throw new IllegalArgumentException("Technique already linked to this training");
        }

        TrainingTechnique trainingTechnique = new TrainingTechnique(
                training,
                technique,
                request.note()
        );

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

        trainingTechnique.setNote(request.note());

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

    private TrainingTechniqueResponse toResponse(TrainingTechnique trainingTechnique) {
        Technique technique = trainingTechnique.getTechnique();

        return new TrainingTechniqueResponse(
                technique.getId(),
                technique.getName(),
                technique.getCategory(),
                technique.getDescription(),
                trainingTechnique.getNote()
        );
    }
}