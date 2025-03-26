ALTER TABLE application.applications_info DROP COLUMN IF EXISTS lk_publication_type_id;
ALTER TABLE application.applications_info DROP CONSTRAINT IF EXISTS fk_reference_lk_publication_type;

ALTER TABLE application.publication_issue
ADD COLUMN IF NOT EXISTS issuing_date_hijri VARCHAR(255);

ALTER TABLE application.application_publication
ADD COLUMN IF NOT EXISTS publication_date_hijri VARCHAR(255);