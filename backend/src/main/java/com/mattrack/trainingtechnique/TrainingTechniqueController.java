package com.mattrack.trainingtechnique;

import com.mattrack.trainingtechnique.dto.AddTechniqueToTrainingRequest;
import com.mattrack.trainingtechnique.dto.TrainingTechniqueResponse;
import com.mattrack.trainingtechnique.dto.UpdateTrainingTechniqueNoteRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainings/{trainingId}/techniques")
public class TrainingTechniqueController {

    private final TrainingTechniqueService trainingTechniqueService;

    public TrainingTechniqueController(TrainingTechniqueService trainingTechniqueService) {
        this.trainingTechniqueService = trainingTechniqueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingTechniqueResponse addTechnique(
            Authentication authentication,
            @PathVariable UUID trainingId,
            @Valid @RequestBody AddTechniqueToTrainingRequest request
    ) {
        return trainingTechniqueService.addTechnique(
                authentication.getName(),
                trainingId,
                request
        );
    }

    @GetMapping
    public List<TrainingTechniqueResponse> findTechniquesByTraining(
            Authentication authentication,
            @PathVariable UUID trainingId
    ) {
        return trainingTechniqueService.findTechniquesByTraining(
                authentication.getName(),
                trainingId
        );
    }

    @PutMapping("/{techniqueId}")
    public TrainingTechniqueResponse updateNote(
            Authentication authentication,
            @PathVariable UUID trainingId,
            @PathVariable UUID techniqueId,
            @Valid @RequestBody UpdateTrainingTechniqueNoteRequest request
    ) {
        return trainingTechniqueService.updateNote(
                authentication.getName(),
                trainingId,
                techniqueId,
                request
        );
    }

    @DeleteMapping("/{techniqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTechnique(
            Authentication authentication,
            @PathVariable UUID trainingId,
            @PathVariable UUID techniqueId
    ) {
        trainingTechniqueService.removeTechnique(
                authentication.getName(),
                trainingId,
                techniqueId
        );
    }
}