package com.mattrack.technique;

import com.mattrack.sport.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechniqueRepository extends JpaRepository<Technique, UUID> {

    List<Technique> findAllByOrderByNameAsc();

    List<Technique> findAllBySportTypeOrderByNameAsc(SportType sportType);

    List<Technique> findAllByCategoryOrderByNameAsc(TechniqueCategory category);

    List<Technique> findAllBySportTypeAndCategoryOrderByNameAsc(SportType sportType, TechniqueCategory category);

    boolean existsByNameIgnoreCaseAndSportType(String name, SportType sportType);

    Optional<Technique> findByNameIgnoreCaseAndSportType(String name, SportType sportType);
}
