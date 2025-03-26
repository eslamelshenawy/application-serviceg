--liquibase formatted sql
-- changeset application-service:v18_category_publication_days.sql
ALTER TABLE application.lk_application_category ADD opposition_days int4 NULL;

ALTER TABLE application.lk_application_category ADD publication_auto_approval_days int4 NULL;
