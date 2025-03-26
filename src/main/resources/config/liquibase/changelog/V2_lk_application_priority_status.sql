--liquibase formatted sql
-- changeset application-service:add_code_collumn_lk_application_priority_status.sql

ALTER TABLE application.application_priority
    ADD comment TEXT;


ALTER TABLE application.lk_application_priority_status
    ADD code varchar(255);


UPDATE application.lk_application_priority_status
SET code = 'ACCEPTED'
WHERE application.lk_application_priority_status.id = 1;

UPDATE application.lk_application_priority_status
SET code = 'REJECTED'
WHERE application.lk_application_priority_status.id = 2;

UPDATE application.lk_application_priority_status
SET code = 'NOT_DEFINED'
WHERE application.lk_application_priority_status.id = 3;

UPDATE application.lk_application_priority_status
SET code = 'DELETED'
WHERE application.lk_application_priority_status.id = 4;

