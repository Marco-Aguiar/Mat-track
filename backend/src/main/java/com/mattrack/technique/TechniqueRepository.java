package com.mattrack.technique;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechniqueRepository extends JpaRepository<Technique, UUID> {

    List<Technique> findAllByOrderByNameAsc();

    List<Technique> findAllByCategoryOrderByNameAsc(TechniqueCategory category);

    boolean existsByNameIgnoreCase(String name);

    Optional<Technique> findByNameIgnoreCase(String name);
}