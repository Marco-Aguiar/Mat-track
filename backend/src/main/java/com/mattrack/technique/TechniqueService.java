package com.mattrack.technique;

import com.mattrack.common.PageResponse;
import com.mattrack.sport.SportType;
import com.mattrack.technique.dto.TechniqueProgressionResponse;
import com.mattrack.technique.dto.TechniqueRequest;
import com.mattrack.technique.dto.TechniqueResponse;
import com.mattrack.trainingtechnique.TrainingTechniqueRepository;
import com.mattrack.user.User;
import com.mattrack.user.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TechniqueService {

    private final TechniqueRepository techniqueRepository;
    private final UserRepository userRepository;
    private final TrainingTechniqueRepository trainingTechniqueRepository;

    public TechniqueService(
            TechniqueRepository techniqueRepository,
            UserRepository userRepository,
            TrainingTechniqueRepository trainingTechniqueRepository
    ) {
        this.techniqueRepository = techniqueRepository;
        this.userRepository = userRepository;
        this.trainingTechniqueRepository = trainingTechniqueRepository;
    }

    @Transactional
    public TechniqueResponse create(String email, TechniqueRequest request) {
        User user = findUser(email);
        SportType sportType = resolveSportType(request.sportType());

        if (techniqueRepository.existsByNameIgnoreCaseAndSportTypeVisibleToUser(request.name(), sportType, user)) {
            throw new IllegalArgumentException("Technique already exists for sport type " + sportType);
        }

        Technique technique = new Technique();
        technique.setName(request.name());
        technique.setSportType(sportType);
        technique.setCategory(request.category());
        technique.setDescription(request.description());
        technique.setCreatedBy(user);

        return toResponse(techniqueRepository.save(technique), user.getId());
    }

    @Transactional(readOnly = true)
    public PageResponse<TechniqueResponse> findAll(String email, SportType sportType, TechniqueCategory category, int page, int size) {
        User user = findUser(email);
        var pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        var techniques = (sportType != null && category != null)
                ? techniqueRepository.findVisibleByUserAndSportTypeAndCategory(user, sportType, category, pageable)
                : (sportType != null)
                ? techniqueRepository.findVisibleByUserAndSportType(user, sportType, pageable)
                : (category != null)
                ? techniqueRepository.findVisibleByUserAndCategory(user, category, pageable)
                : techniqueRepository.findVisibleByUser(user, pageable);

        return PageResponse.from(techniques.map(t -> toResponse(t, user.getId())));
    }

    @Transactional(readOnly = true)
    public TechniqueResponse findById(String email, UUID id) {
        User user = findUser(email);
        Technique technique = findVisibleTechnique(user, id);
        return toResponse(technique, user.getId());
    }

    @Transactional
    public TechniqueResponse update(String email, UUID id, TechniqueRequest request) {
        User user = findUser(email);
        Technique technique = findOwnedTechnique(user, id);
        SportType sportType = request.sportType() == null ? technique.getSportType() : request.sportType();

        techniqueRepository.findByNameIgnoreCaseAndSportTypeVisibleToUser(request.name(), sportType, user)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Technique already exists for sport type " + sportType);
                });

        technique.setName(request.name());
        technique.setSportType(sportType);
        technique.setCategory(request.category());
        technique.setDescription(request.description());

        return toResponse(techniqueRepository.save(technique), user.getId());
    }

    @Transactional
    public void delete(String email, UUID id) {
        User user = findUser(email);
        Technique technique = findOwnedTechnique(user, id);
        techniqueRepository.delete(technique);
    }

    @Transactional(readOnly = true)
    public List<TechniqueProgressionResponse> getProgression(
            String email,
            UUID techniqueId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        User user = findUser(email);
        findVisibleTechnique(user, techniqueId);

        return trainingTechniqueRepository
                .findProgressionByTechniqueAndUser(techniqueId, email, startDate, endDate)
                .stream()
                .map(tt -> new TechniqueProgressionResponse(
                        tt.getTraining().getTrainingDate(),
                        tt.getTraining().getId(),
                        tt.getSets(),
                        tt.getReps(),
                        tt.getLoadKg(),
                        tt.getDistanceKm(),
                        tt.getDurationSeconds(),
                        tt.getNote()
                ))
                .toList();
    }

    private TechniqueResponse toResponse(Technique technique, UUID currentUserId) {
        boolean owned = technique.getCreatedBy() != null
                && technique.getCreatedBy().getId().equals(currentUserId);
        return new TechniqueResponse(
                technique.getId(),
                technique.getName(),
                technique.getSportType(),
                technique.getCategory(),
                technique.getDescription(),
                owned,
                technique.getCreatedAt(),
                technique.getUpdatedAt()
        );
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private SportType resolveSportType(SportType sportType) {
        return sportType == null ? SportType.JIU_JITSU : sportType;
    }

    private Technique findVisibleTechnique(User user, UUID id) {
        Technique technique = techniqueRepository.findById(id)
                .orElseThrow(TechniqueNotFoundException::new);
        if (technique.getCreatedBy() != null && !technique.getCreatedBy().getId().equals(user.getId())) {
            throw new TechniqueNotFoundException();
        }
        return technique;
    }

    private Technique findOwnedTechnique(User user, UUID id) {
        Technique technique = techniqueRepository.findById(id)
                .orElseThrow(TechniqueNotFoundException::new);
        if (technique.getCreatedBy() == null || !technique.getCreatedBy().getId().equals(user.getId())) {
            throw new TechniqueNotOwnedException();
        }
        return technique;
    }

}
