package com.mattrack.trainingtechnique;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class TrainingTechniqueId implements Serializable {

    @Column(name = "training_id")
    private UUID trainingId;

    @Column(name = "technique_id")
    private UUID techniqueId;

    public TrainingTechniqueId() {
    }

    public TrainingTechniqueId(UUID trainingId, UUID techniqueId) {
        this.trainingId = trainingId;
        this.techniqueId = techniqueId;
    }

    public UUID getTrainingId() {
        return trainingId;
    }

    public UUID getTechniqueId() {
        return techniqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingTechniqueId that)) return false;
        return Objects.equals(trainingId, that.trainingId)
                && Objects.equals(techniqueId, that.techniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, techniqueId);
    }
}