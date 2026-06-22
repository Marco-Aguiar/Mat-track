package com.mattrack.training;

import com.mattrack.common.PageResponse;
import com.mattrack.sport.SportType;
import com.mattrack.training.dto.TrainingRequest;
import com.mattrack.training.dto.TrainingResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public PageResponse<TrainingResponse> findAll(
            Authentication authentication,
            @RequestParam(required = false) SportType sportType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return trainingService.findAll(authentication.getName(), sportType, startDate, endDate, page, size);
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
