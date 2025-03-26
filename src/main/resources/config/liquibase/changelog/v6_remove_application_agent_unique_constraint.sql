--liquibase formatted sql
-- changeset application-service:v6_remove_application_agent_unique_constraint.sql

ALTER TABLE application.application_agents DROP CONSTRAINT uniqu_application_id_agent_id;

