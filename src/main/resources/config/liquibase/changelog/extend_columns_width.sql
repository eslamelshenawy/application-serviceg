ALTER TABLE application.application_notes ALTER COLUMN description TYPE text USING description::text;

ALTER TABLE application.applications_info ALTER COLUMN classification_notes TYPE text USING classification_notes::text;
