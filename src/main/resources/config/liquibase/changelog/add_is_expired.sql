--liquibase formatted sql
-- changeset application-service:add_is_expired.sql

alter table application.application_priority add column is_expired bool DEFAULT FALSE;