--liquibase formatted sql
-- changeset application-service:v10_publication_type.sql
ALTER TABLE application.applications_info ADD lk_publication_type_id int4 NULL;

ALTER TABLE application.lk_publication_type
    ADD CONSTRAINT pk_lk_publication_type PRIMARY KEY (id);

ALTER TABLE application.applications_info ADD CONSTRAINT fk_reference_lk_publication_type
    FOREIGN KEY (lk_publication_type_id) REFERENCES application.lk_publication_type(id);

