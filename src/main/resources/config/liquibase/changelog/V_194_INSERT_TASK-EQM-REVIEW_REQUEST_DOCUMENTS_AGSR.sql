INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_types), 'REVIEW_REQUEST_DOCUMENTS_AGSR',
        'مراجعه طلب تغيير وكيل', 'Review Agent Substitution Request'
       );

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'REVIEW_REQUEST_DOCUMENTS_AGSR_ATTACHMENTS',
        'هل تم ارفاق المستندات بصورة صحيحه ؟', 'Attachments ?', 'BOOLEAN');
INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'REVIEW_REQUEST_DOCUMENTS_AGSR_INFO',
        'هل تم تقديم الطلب بصورة صحيحه ؟', 'Was the request information clear ?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='REVIEW_REQUEST_DOCUMENTS_AGSR'),
        (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'REVIEW_REQUEST_DOCUMENTS_AGSR_ATTACHMENTS'));
INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='REVIEW_REQUEST_DOCUMENTS_AGSR'),
        (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'REVIEW_REQUEST_DOCUMENTS_AGSR_INFO'));
