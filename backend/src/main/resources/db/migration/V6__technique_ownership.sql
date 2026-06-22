ALTER TABLE techniques
    ADD COLUMN IF NOT EXISTS created_by UUID REFERENCES users(id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_techniques_created_by
    ON techniques (created_by);
