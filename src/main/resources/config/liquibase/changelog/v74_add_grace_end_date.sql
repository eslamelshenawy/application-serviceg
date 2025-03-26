--liquibase formatted sql
-- changeset application-service:v74_add_grace_end_date.sql


ALTER TABLE application.application_installments ADD if not exists grace_end_date timestamp NULL;
