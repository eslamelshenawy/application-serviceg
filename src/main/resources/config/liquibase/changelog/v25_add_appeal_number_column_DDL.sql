-- liquibase formatted sql
-- changeset application-service:v25_add_appeal_number_column_DDL.sql

-- ADD COLUMN

ALTER TABLE application.appeal_request ADD appeal_request_number varchar(255) NULL;
