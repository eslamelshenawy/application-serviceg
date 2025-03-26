--liquibase formatted sql
-- changeset application-service:rename-schema.sql
ALTER SCHEMA application
    RENAME TO application_old;