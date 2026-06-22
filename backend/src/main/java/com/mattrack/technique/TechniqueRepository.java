package com.mattrack.technique;

import com.mattrack.sport.SportType;
import com.mattrack.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TechniqueRepository extends JpaRepository<Technique, UUID> {

    @Query("""
            SELECT t FROM Technique t
            WHERE (t.createdBy IS NULL OR t.createdBy = :user)
            ORDER BY t.name ASC
            """)
    Page<Technique> findVisibleByUser(@Param("user") User user, Pageable pageable);

    @Query("""
            SELECT t FROM Technique t
            WHERE (t.createdBy IS NULL OR t.createdBy = :user)
              AND t.sportType = :sportType
            ORDER BY t.name ASC
            """)
    Page<Technique> findVisibleByUserAndSportType(@Param("user") User user, @Param("sportType") SportType sportType, Pageable pageable);

    @Query("""
            SELECT t FROM Technique t
            WHERE (t.createdBy IS NULL OR t.createdBy = :user)
              AND t.category = :category
            ORDER BY t.name ASC
            """)
    Page<Technique> findVisibleByUserAndCategory(@Param("user") User user, @Param("category") TechniqueCategory category, Pageable pageable);

    @Query("""
            SELECT t FROM Technique t
            WHERE (t.createdBy IS NULL OR t.createdBy = :user)
              AND t.sportType = :sportType
              AND t.category = :category
            ORDER BY t.name ASC
            """)
    Page<Technique> findVisibleByUserAndSportTypeAndCategory(@Param("user") User user, @Param("sportType") SportType sportType, @Param("category") TechniqueCategory category, Pageable pageable);

    @Query("""
            SELECT COUNT(t) > 0 FROM Technique t
            WHERE LOWER(t.name) = LOWER(:name)
              AND t.sportType = :sportType
              AND (t.createdBy IS NULL OR t.createdBy = :user)
            """)
    boolean existsByNameIgnoreCaseAndSportTypeVisibleToUser(@Param("name") String name, @Param("sportType") SportType sportType, @Param("user") User user);

    @Query("""
            SELECT t FROM Technique t
            WHERE LOWER(t.name) = LOWER(:name)
              AND t.sportType = :sportType
              AND (t.createdBy IS NULL OR t.createdBy = :user)
            """)
    Optional<Technique> findByNameIgnoreCaseAndSportTypeVisibleToUser(@Param("name") String name, @Param("sportType") SportType sportType, @Param("user") User user);

    boolean existsByNameIgnoreCaseAndSportType(String name, SportType sportType);

    Optional<Technique> findByNameIgnoreCaseAndSportType(String name, SportType sportType);
}
