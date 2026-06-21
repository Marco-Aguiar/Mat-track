package com.mattrack.trainingtechnique;

import com.mattrack.technique.Technique;
import com.mattrack.training.Training;
import jakarta.persistence.*;

@Entity
@Table(name = "training_techniques")
public class TrainingTechnique {

    @EmbeddedId
    private TrainingTechniqueId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("trainingId")
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("techniqueId")
    @JoinColumn(name = "technique_id", nullable = false)
    private Technique technique;

    @Column(columnDefinition = "TEXT")
    private String note;

    public TrainingTechnique() {
    }

    public TrainingTechnique(Training training, Technique technique, String note) {
        this.training = training;
        this.technique = technique;
        this.note = note;
        this.id = new TrainingTechniqueId(training.getId(), technique.getId());
    }

    public TrainingTechniqueId getId() {
        return id;
    }

    public Training getTraining() {
        return training;
    }

    public Technique getTechnique() {
        return technique;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}