CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE app_users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    belt VARCHAR(30) NOT NULL DEFAULT 'WHITE',
    current_weight_kg NUMERIC(5,2),
    academy VARCHAR(120),
    goal VARCHAR(40),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE techniques (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(120) NOT NULL,
    category VARCHAR(40) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE trainings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES app_users(id),
    training_date DATE NOT NULL,
    type VARCHAR(40) NOT NULL,
    duration_minutes INTEGER,
    rounds SMALLINT,
    intensity SMALLINT CHECK (intensity BETWEEN 1 AND 5),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE training_techniques (
    training_id UUID NOT NULL REFERENCES trainings(id) ON DELETE CASCADE,
    technique_id UUID NOT NULL REFERENCES techniques(id),
    note TEXT,
    PRIMARY KEY (training_id, technique_id)
);
