-- liquibase formatted sql
-- changeset application-service:v38_add_serial_col.sql

alter table application.certificates_request add column serial BIGSERIAL;
