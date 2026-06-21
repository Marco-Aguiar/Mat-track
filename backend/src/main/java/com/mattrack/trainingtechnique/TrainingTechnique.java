package com.mattrack.trainingtechnique;

import com.mattrack.technique.Technique;
import com.mattrack.training.Training;
import jakarta.persistence.*;

import java.math.BigDecimal;

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

    private Short sets;

    private Short reps;

    @Column(name = "load_kg", precision = 6, scale = 2)
    private BigDecimal loadKg;

    @Column(name = "distance_km", precision = 6, scale = 2)
    private BigDecimal distanceKm;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(columnDefinition = "TEXT")
    private String note;

    public TrainingTechnique() {
    }

    public TrainingTechnique(Training training, Technique technique) {
        this.training = training;
        this.technique = technique;
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

    public Short getSets() {
        return sets;
    }

    public void setSets(Short sets) {
        this.sets = sets;
    }

    public Short getReps() {
        return reps;
    }

    public void setReps(Short reps) {
        this.reps = reps;
    }

    public BigDecimal getLoadKg() {
        return loadKg;
    }

    public void setLoadKg(BigDecimal loadKg) {
        this.loadKg = loadKg;
    }

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
