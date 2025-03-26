--liquibase formatted sql
-- changeset application-service:v87_add_quality_control_script.sql

INSERT INTO application.lk_task_eqm_types (id,code,name_ar,name_en)
VALUES (9,'QUALITY_CONTROL','مراقبة جوده','Quality control');


