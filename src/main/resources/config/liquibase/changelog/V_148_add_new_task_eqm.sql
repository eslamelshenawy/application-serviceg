INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_types), 'REVIEW_REQUEST_DOCUMENTS_PPR', 'طلب التماس اضافه او تعديل اسبقيه', 'Priority addition or modification Petition Request');
INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_types), 'REVIEW_REQUEST_DOCUMENTS_PPMR', 'طلب اضافه او تعديل الاسبقيه', 'Priority Modify Request');

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'PRIORITY_PETITION_REQUEST_ATTACHMENTS', 'هل تم ارفاق المستندات بصورة صحيحه ؟', 'Attachments ?', 'BOOLEAN');
INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'PRIORITY_PETITION_REQUEST_INFO', 'هل تم تقديم الطلب بصورة صحيحه ؟', 'Was the request information clear ?', 'BOOLEAN');
INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'PRIORITY_MODIFY_REQUEST_ATTACHMENTS', 'هل تم ارفاق مستندات الاسبقيه بعد التعديل بصورة صحيحه ؟', 'Attachments ?', 'BOOLEAN');
INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'PRIORITY_MODIFY_REQUEST_INFO', 'هل تم تعديل الطلب بصورة صحيحه ؟', 'Was the request modified correctly ?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
    VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='REVIEW_REQUEST_DOCUMENTS_PPR'),
            (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'PRIORITY_PETITION_REQUEST_ATTACHMENTS'));
INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
    VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='REVIEW_REQUEST_DOCUMENTS_PPR'),
            (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'PRIORITY_PETITION_REQUEST_INFO'));
INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
    VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='REVIEW_REQUEST_DOCUMENTS_PPMR'),
            (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'PRIORITY_MODIFY_REQUEST_ATTACHMENTS'));
INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
    VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='REVIEW_REQUEST_DOCUMENTS_PPMR'),
            (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'PRIORITY_MODIFY_REQUEST_INFO'));

