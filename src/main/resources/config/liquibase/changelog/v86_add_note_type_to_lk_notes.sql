--liquibase formatted sql
-- changeset application-service:v86_add_note_type_to_lk_notes.sql

ALTER TABLE application.lk_notes ADD notes_type_enum varchar NULL;

UPDATE application.lk_notes
SET notes_type_enum='APPLICANT'
;

ALTER TABLE application.lk_notes ALTER COLUMN notes_type_enum SET NOT NULL;

