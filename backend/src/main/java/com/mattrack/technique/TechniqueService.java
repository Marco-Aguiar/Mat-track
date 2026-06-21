package com.mattrack.technique;

import com.mattrack.technique.dto.TechniqueRequest;
import com.mattrack.technique.dto.TechniqueResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TechniqueService {

    private final TechniqueRepository techniqueRepository;

    public TechniqueService(TechniqueRepository techniqueRepository) {
        this.techniqueRepository = techniqueRepository;
    }

    @Transactional
    public TechniqueResponse create(TechniqueRequest request) {
        if (techniqueRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException("Technique already exists");
        }

        Technique technique = new Technique();
        technique.setName(request.name());
        technique.setCategory(request.category());
        technique.setDescription(request.description());

        return toResponse(techniqueRepository.save(technique));
    }

    @Transactional(readOnly = true)
    public List<TechniqueResponse> findAll(TechniqueCategory category) {
        List<Technique> techniques;

        if (category == null) {
            techniques = techniqueRepository.findAllByOrderByNameAsc();
        } else {
            techniques = techniqueRepository.findAllByCategoryOrderByNameAsc(category);
        }

        return techniques
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TechniqueResponse findById(UUID id) {
        Technique technique = findTechnique(id);
        return toResponse(technique);
    }

    @Transactional
    public TechniqueResponse update(UUID id, TechniqueRequest request) {
        Technique technique = findTechnique(id);

        techniqueRepository.findByNameIgnoreCase(request.name())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Technique already exists");
                });

        technique.setName(request.name());
        technique.setCategory(request.category());
        technique.setDescription(request.description());

        return toResponse(techniqueRepository.save(technique));
    }

    @Transactional
    public void delete(UUID id) {
        Technique technique = findTechnique(id);
        techniqueRepository.delete(technique);
    }

    private Technique findTechnique(UUID id) {
        return techniqueRepository.findById(id)
                .orElseThrow(TechniqueNotFoundException::new);
    }

    private TechniqueResponse toResponse(Technique technique) {
        return new TechniqueResponse(
                technique.getId(),
                technique.getName(),
                technique.getCategory(),
                technique.getDescription(),
                technique.getCreatedAt()
        );
    }
}