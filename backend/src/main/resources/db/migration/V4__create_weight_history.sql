CREATE TABLE weight_history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    weight_kg NUMERIC(5,2) NOT NULL,
    measured_at DATE NOT NULL,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uk_weight_history_user_measured_at UNIQUE (user_id, measured_at),
    CONSTRAINT chk_weight_history_weight CHECK (weight_kg >= 20 AND weight_kg <= 300)
);