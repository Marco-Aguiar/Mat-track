package com.mattrack.training;

import com.mattrack.sport.SportType;
import com.mattrack.training.dto.TrainingRequest;
import com.mattrack.training.dto.TrainingResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingResponse create(
            Authentication authentication,
            @Valid @RequestBody TrainingRequest request
    ) {
        return trainingService.create(authentication.getName(), request);
    }

    @GetMapping
    public List<TrainingResponse> findAll(
            Authentication authentication,
            @RequestParam(required = false) SportType sportType
    ) {
        return trainingService.findAll(authentication.getName(), sportType);
    }

    @GetMapping("/{id}")
    public TrainingResponse findById(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        return trainingService.findById(authentication.getName(), id);
    }

    @PutMapping("/{id}")
    public TrainingResponse update(
            Authentication authentication,
            @PathVariable UUID id,
            @Valid @RequestBody TrainingRequest request
    ) {
        return trainingService.update(
                authentication.getName(),
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        trainingService.delete(authentication.getName(), id);
    }
}
