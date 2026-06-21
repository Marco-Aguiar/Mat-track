package com.mattrack.technique;

import com.mattrack.technique.dto.TechniqueRequest;
import com.mattrack.technique.dto.TechniqueResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public TechniqueResponse create(@Valid @RequestBody TechniqueRequest request) {
        return techniqueService.create(request);
    }

    @GetMapping
    public List<TechniqueResponse> findAll(
            @RequestParam(required = false) TechniqueCategory category
    ) {
        return techniqueService.findAll(category);
    }

    @GetMapping("/{id}")
    public TechniqueResponse findById(@PathVariable UUID id) {
        return techniqueService.findById(id);
    }

    @PutMapping("/{id}")
    public TechniqueResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody TechniqueRequest request
    ) {
        return techniqueService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        techniqueService.delete(id);
    }
}