INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES((select max(id) + 1 from application.lk_task_eqm_types), 'REVIEW_REQUEST_DOCUMENTS_AENA', 'تعديل البيانات', 'Edit Data');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), 6);

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), 7);


