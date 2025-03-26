--liquibase formatted sql
-- changeset application-service:v84_eqm_comprehensive_evaluation.sql

-- clean data
delete from application.task_eqm_type_items where lk_task_eqm_type_id = (select id from application.lk_task_eqm_types where code = 'APP_COMPREHENSIVE_EVALUATION');
delete from application.lk_task_eqm_types where code = 'APP_COMPREHENSIVE_EVALUATION';

-- insert type
INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES((select max(id) + 1 from application.lk_task_eqm_types), 'APP_COMPREHENSIVE_EVALUATION', 'التقييم الشامل', 'Comprehensive Evaluation');



-- map items to the new type
INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), 1);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), 2);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), 3);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), 4);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), 5);

