package com.mattrack.training;

import com.mattrack.training.dto.TrainingRequest;
import com.mattrack.training.dto.TrainingResponse;
import com.mattrack.user.User;
import com.mattrack.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<TrainingResponse> findAll(String email) {
        return trainingRepository
                .findAllByUserEmailOrderByTrainingDateDesc(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrainingResponse findById(String email, UUID id) {
        return toResponse(findOwnedTraining(email, id));
    }

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
        training.setTrainingDate(request.trainingDate());
        training.setType(request.type());
        training.setDurationMinutes(request.durationMinutes());
        training.setRounds(request.rounds());
        training.setIntensity(request.intensity());
        training.setNotes(request.notes());
    }

    private TrainingResponse toResponse(Training training) {
        return new TrainingResponse(
                training.getId(),
                training.getTrainingDate(),
                training.getType(),
                training.getDurationMinutes(),
                training.getRounds(),
                training.getIntensity(),
                training.getNotes(),
                training.getCreatedAt(),
                training.getUpdatedAt()
        );
    }
}