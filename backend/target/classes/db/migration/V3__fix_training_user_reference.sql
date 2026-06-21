ALTER TABLE trainings
    DROP CONSTRAINT trainings_user_id_fkey;

ALTER TABLE trainings
    ADD CONSTRAINT fk_trainings_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE;