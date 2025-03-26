DELETE FROM application.application_section_notes;

ALTER TABLE application.application_section_notes ADD CONSTRAINT application_section_notes_pk PRIMARY KEY (id);

ALTER TABLE application.application_section_notes ALTER COLUMN is_deleted SET NOT NULL;

ALTER TABLE application.application_section_notes ALTER COLUMN id SET NOT NULL;
