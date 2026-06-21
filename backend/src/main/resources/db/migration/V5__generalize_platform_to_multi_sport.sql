ALTER TABLE users
    ADD COLUMN IF NOT EXISTS primary_sport VARCHAR(40) NOT NULL DEFAULT 'JIU_JITSU';

ALTER TABLE techniques
    ADD COLUMN IF NOT EXISTS sport_type VARCHAR(40) NOT NULL DEFAULT 'JIU_JITSU',
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT NOW();

ALTER TABLE trainings
    ADD COLUMN IF NOT EXISTS sport_type VARCHAR(40) NOT NULL DEFAULT 'JIU_JITSU',
    ADD COLUMN IF NOT EXISTS distance_km NUMERIC(6,2),
    ADD COLUMN IF NOT EXISTS calories_burned INTEGER;

ALTER TABLE training_techniques
    ADD COLUMN IF NOT EXISTS sets SMALLINT,
    ADD COLUMN IF NOT EXISTS reps SMALLINT,
    ADD COLUMN IF NOT EXISTS load_kg NUMERIC(6,2),
    ADD COLUMN IF NOT EXISTS distance_km NUMERIC(6,2),
    ADD COLUMN IF NOT EXISTS duration_seconds INTEGER;

ALTER TABLE trainings
    ADD CONSTRAINT chk_trainings_distance_km CHECK (distance_km IS NULL OR distance_km >= 0),
    ADD CONSTRAINT chk_trainings_calories_burned CHECK (calories_burned IS NULL OR calories_burned >= 0);

ALTER TABLE training_techniques
    ADD CONSTRAINT chk_training_techniques_sets CHECK (sets IS NULL OR sets >= 0),
    ADD CONSTRAINT chk_training_techniques_reps CHECK (reps IS NULL OR reps >= 0),
    ADD CONSTRAINT chk_training_techniques_load_kg CHECK (load_kg IS NULL OR load_kg >= 0),
    ADD CONSTRAINT chk_training_techniques_distance_km CHECK (distance_km IS NULL OR distance_km >= 0),
    ADD CONSTRAINT chk_training_techniques_duration_seconds CHECK (duration_seconds IS NULL OR duration_seconds >= 0);

CREATE INDEX IF NOT EXISTS idx_trainings_user_sport_date
    ON trainings (user_id, sport_type, training_date DESC);

CREATE INDEX IF NOT EXISTS idx_techniques_sport_category
    ON techniques (sport_type, category);

CREATE UNIQUE INDEX IF NOT EXISTS uk_techniques_name_sport
    ON techniques (LOWER(name), sport_type);
