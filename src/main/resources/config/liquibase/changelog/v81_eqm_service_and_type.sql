--liquibase formatted sql
-- changeset application-service:v81_eqm_service_and_type.sql
truncate table application.task_eqm cascade;

ALTER TABLE application.task_eqm add column if not exists service_id int8 NULL;
ALTER TABLE application.task_eqm ADD column if not exists lk_task_eqm_type_id int4 NULL;

ALTER TABLE application.task_eqm ADD CONSTRAINT fkj6outiffy6qn6hv7p584nl46u FOREIGN KEY (lk_task_eqm_type_id) REFERENCES application.lk_task_eqm_types(id);