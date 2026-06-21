package com.mattrack.technique;

import com.mattrack.sport.SportType;
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
        SportType sportType = resolveSportType(request.sportType());

        if (techniqueRepository.existsByNameIgnoreCaseAndSportType(request.name(), sportType)) {
            throw new IllegalArgumentException("Technique already exists for sport type " + sportType);
        }

        Technique technique = new Technique();
        technique.setName(request.name());
        technique.setSportType(sportType);
        technique.setCategory(request.category());
        technique.setDescription(request.description());

        return toResponse(techniqueRepository.save(technique));
    }

    @Transactional(readOnly = true)
    public List<TechniqueResponse> findAll(SportType sportType, TechniqueCategory category) {
        List<Technique> techniques;

        if (sportType != null && category != null) {
            techniques = techniqueRepository.findAllBySportTypeAndCategoryOrderByNameAsc(sportType, category);
        } else if (sportType != null) {
            techniques = techniqueRepository.findAllBySportTypeOrderByNameAsc(sportType);
        } else if (category != null) {
            techniques = techniqueRepository.findAllByCategoryOrderByNameAsc(category);
        } else {
            techniques = techniqueRepository.findAllByOrderByNameAsc();
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
        SportType sportType = request.sportType() == null ? technique.getSportType() : request.sportType();

        techniqueRepository.findByNameIgnoreCaseAndSportType(request.name(), sportType)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Technique already exists for sport type " + sportType);
                });

        technique.setName(request.name());
        technique.setSportType(sportType);
        technique.setCategory(request.category());
        technique.setDescription(request.description());

        return toResponse(techniqueRepository.save(technique));
    }

    @Transactional
    public void delete(UUID id) {
        Technique technique = findTechnique(id);
        techniqueRepository.delete(technique);
    }

    private SportType resolveSportType(SportType sportType) {
        return sportType == null ? SportType.JIU_JITSU : sportType;
    }

    private Technique findTechnique(UUID id) {
        return techniqueRepository.findById(id)
                .orElseThrow(TechniqueNotFoundException::new);
    }

    private TechniqueResponse toResponse(Technique technique) {
        return new TechniqueResponse(
                technique.getId(),
                technique.getName(),
                technique.getSportType(),
                technique.getCategory(),
                technique.getDescription(),
                technique.getCreatedAt(),
                technique.getUpdatedAt()
        );
    }
}
