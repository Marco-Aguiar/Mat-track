package com.mattrack.training;

import com.mattrack.sport.SportType;

public enum TrainingType {
    GI(SportType.JIU_JITSU),
    NO_GI(SportType.JIU_JITSU),
    OPEN_MAT(SportType.JIU_JITSU),
    DRILLING(SportType.JIU_JITSU),
    COMPETITION(SportType.JIU_JITSU),

    STRENGTH(SportType.STRENGTH_TRAINING),
    HYPERTROPHY(SportType.STRENGTH_TRAINING),
    POWER(SportType.STRENGTH_TRAINING),
    UPPER_BODY(SportType.STRENGTH_TRAINING),
    LOWER_BODY(SportType.STRENGTH_TRAINING),

    FUNCTIONAL(SportType.FUNCTIONAL_TRAINING),
    HIIT(SportType.FUNCTIONAL_TRAINING),
    CIRCUIT(SportType.FUNCTIONAL_TRAINING),
    MOBILITY(SportType.FUNCTIONAL_TRAINING),
    CONDITIONING(SportType.FUNCTIONAL_TRAINING),

    EASY_RUN(SportType.RUNNING),
    LONG_RUN(SportType.RUNNING),
    INTERVAL_RUN(SportType.RUNNING),
    TEMPO_RUN(SportType.RUNNING),
    RECOVERY_RUN(SportType.RUNNING);

    private final SportType sportType;

    TrainingType(SportType sportType) {
        this.sportType = sportType;
    }

    public SportType getSportType() {
        return sportType;
    }

    public boolean belongsTo(SportType sportType) {
        return this.sportType == sportType;
    }
}
