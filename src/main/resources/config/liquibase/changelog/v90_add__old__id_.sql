
ALTER TABLE application.applications_info
    ADD COLUMN IF NOT EXISTS id_old varchar(255) NULL;

