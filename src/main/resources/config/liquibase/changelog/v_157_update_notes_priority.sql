ALTER TABLE application.application_notes ADD priority_id int4 NULL;

ALTER TABLE application.application_notes ADD CONSTRAINT application_notes_fk FOREIGN KEY (priority_id) REFERENCES application.application_priority(id);
