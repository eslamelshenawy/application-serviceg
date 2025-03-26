ALTER TABLE application.lk_classification_units ADD version_id int4 NULL;
ALTER TABLE application.lk_classification_units ADD CONSTRAINT lk_classification_units_fk FOREIGN KEY (version_id) REFERENCES application.lk_classification_versions(id);
