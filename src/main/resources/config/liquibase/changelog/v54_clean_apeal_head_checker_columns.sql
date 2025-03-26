--liquibase formatted sql
-- changeset application-service:v54_clean_apeal_head_checker_columns.sql

ALTER TABLE application.appeal_request DROP COLUMN head_checker_notes_to_checker;
ALTER TABLE application.appeal_request DROP COLUMN head_checker_confirmed;