package com.mattrack.technique;

import com.mattrack.common.PageResponse;
import com.mattrack.sport.SportType;
import com.mattrack.technique.dto.TechniqueProgressionResponse;
import com.mattrack.technique.dto.TechniqueRequest;
import com.mattrack.technique.dto.TechniqueResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/techniques")
public class TechniqueController {

    private final TechniqueService techniqueService;

    public TechniqueController(TechniqueService techniqueService) {
        this.techniqueService = techniqueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechniqueResponse create(
            Authentication authentication,
            @Valid @RequestBody TechniqueRequest request
    ) {
        return techniqueService.create(authentication.getName(), request);
    }

    @GetMapping
    public PageResponse<TechniqueResponse> findAll(
            Authentication authentication,
            @RequestParam(required = false) SportType sportType,
            @RequestParam(required = false) TechniqueCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return techniqueService.findAll(authentication.getName(), sportType, category, page, size);
    }

    @GetMapping("/{id}")
    public TechniqueResponse findById(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        return techniqueService.findById(authentication.getName(), id);
    }

    @PutMapping("/{id}")
    public TechniqueResponse update(
            Authentication authentication,
            @PathVariable UUID id,
            @Valid @RequestBody TechniqueRequest request
    ) {
        return techniqueService.update(authentication.getName(), id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        techniqueService.delete(authentication.getName(), id);
    }

    @GetMapping("/{id}/progression")
    public List<TechniqueProgressionResponse> getProgression(
            Authentication authentication,
            @PathVariable UUID id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return techniqueService.getProgression(authentication.getName(), id, startDate, endDate);
    }
}
