package com.mattrack.trainingtechnique;

import com.mattrack.sport.SportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingTechniqueRepository extends JpaRepository<TrainingTechnique, TrainingTechniqueId> {

    List<TrainingTechnique> findAllByTraining_IdOrderByTechnique_NameAsc(UUID trainingId);

    Optional<TrainingTechnique> findByTraining_IdAndTechnique_Id(UUID trainingId, UUID techniqueId);

    boolean existsByTraining_IdAndTechnique_Id(UUID trainingId, UUID techniqueId);

    @Query("""
            SELECT tt.technique.id, tt.technique.name, tt.technique.sportType, tt.technique.category, COUNT(tt)
            FROM TrainingTechnique tt
            WHERE tt.training.user.email = :email
              AND (:sportType IS NULL OR tt.training.sportType = :sportType)
            GROUP BY tt.technique.id, tt.technique.name, tt.technique.sportType, tt.technique.category
            ORDER BY COUNT(tt) DESC, tt.technique.name ASC
            """)
    List<Object[]> findMostTrainedTechniquesByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT tt.technique.sportType, tt.technique.category, COUNT(tt)
            FROM TrainingTechnique tt
            WHERE tt.training.user.email = :email
              AND (:sportType IS NULL OR tt.training.sportType = :sportType)
            GROUP BY tt.technique.sportType, tt.technique.category
            ORDER BY COUNT(tt) DESC
            """)
    List<Object[]> countTechniquesByCategoryByUserEmailAndOptionalSportType(
            @Param("email") String email,
            @Param("sportType") SportType sportType
    );

    @Query("""
            SELECT tt.technique.id, tt.technique.name, tt.technique.category, COUNT(tt)
            FROM TrainingTechnique tt
            WHERE tt.training.user.email = :email
            GROUP BY tt.technique.id, tt.technique.name, tt.technique.category
            ORDER BY COUNT(tt) DESC, tt.technique.name ASC
            """)
    List<Object[]> findMostTrainedTechniquesByUserEmail(@Param("email") String email);

    @Query("""
            SELECT tt.technique.category, COUNT(tt)
            FROM TrainingTechnique tt
            WHERE tt.training.user.email = :email
            GROUP BY tt.technique.category
            ORDER BY COUNT(tt) DESC
            """)
    List<Object[]> countTechniquesByCategoryByUserEmail(@Param("email") String email);
}
