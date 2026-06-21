package com.mattrack.weighthistory;

import com.mattrack.weighthistory.dto.WeightHistoryRequest;
import com.mattrack.weighthistory.dto.WeightHistoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/weight-history")
public class WeightHistoryController {

    private final WeightHistoryService weightHistoryService;

    public WeightHistoryController(WeightHistoryService weightHistoryService) {
        this.weightHistoryService = weightHistoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WeightHistoryResponse create(
            Authentication authentication,
            @Valid @RequestBody WeightHistoryRequest request
    ) {
        return weightHistoryService.create(authentication.getName(), request);
    }

    @GetMapping
    public List<WeightHistoryResponse> findAll(Authentication authentication) {
        return weightHistoryService.findAll(authentication.getName());
    }

    @GetMapping("/latest")
    public WeightHistoryResponse findLatest(Authentication authentication) {
        return weightHistoryService.findLatest(authentication.getName());
    }

    @GetMapping("/{id}")
    public WeightHistoryResponse findById(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        return weightHistoryService.findById(authentication.getName(), id);
    }

    @PutMapping("/{id}")
    public WeightHistoryResponse update(
            Authentication authentication,
            @PathVariable UUID id,
            @Valid @RequestBody WeightHistoryRequest request
    ) {
        return weightHistoryService.update(
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
        weightHistoryService.delete(authentication.getName(), id);
    }
}