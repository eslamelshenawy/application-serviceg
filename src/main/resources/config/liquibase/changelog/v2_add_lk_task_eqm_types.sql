INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES((select max(id) + 1 from application.lk_task_eqm_types), 'REVIEW_REQUEST_DOCUMENTS_AETIM', 'تعديل صورة علامة', 'Edit Trademark Image');

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES((select (max(id) + 1) from application.lk_task_eqm_items), 'EDIT_TRADEMARK_IMAGE_DESCRIPTION', 'هل تم وصف العلامة بصورة صحيحة ؟', 'Was the mark described correctly?', 'BOOLEAN');

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES((select (max(id) + 1) from application.lk_task_eqm_items), 'EDIT_TRADEMARK_IMAGE_ATTACHMENT', 'هل تم ارفاق صورة العلامة بعد التعديل بصورة صحيحة ؟',
       'Was the image of the mark attached correctly after modification?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), (select id from application.lk_task_eqm_items where code = 'EDIT_TRADEMARK_IMAGE_DESCRIPTION'));

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES((select max(id) from application.lk_task_eqm_types), (select id from application.lk_task_eqm_items where code = 'EDIT_TRADEMARK_IMAGE_ATTACHMENT'));
