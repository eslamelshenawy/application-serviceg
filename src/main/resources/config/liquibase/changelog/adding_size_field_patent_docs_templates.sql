--liquibase formatted sql
-- changeset application-service:adding_size_field_patent_docs_templates.sql

ALTER TABLE application.documents_template
    ADD COLUMN size int4;
