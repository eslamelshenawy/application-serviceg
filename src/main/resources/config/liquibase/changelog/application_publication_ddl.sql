ALTER TABLE application.application_publication
    ADD COLUMN IF NOT EXISTS
    is_published boolean default false;
