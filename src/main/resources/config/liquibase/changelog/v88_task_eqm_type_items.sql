--liquibase formatted sql
-- changeset application-service:v88_task_eqm_type_items.sql

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id,lk_task_eqm_item_id)
VALUES (9,7);
INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id,lk_task_eqm_item_id)
VALUES (9,6);


