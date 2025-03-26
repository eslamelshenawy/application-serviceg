INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_types), 'APPLICATION_SEARCH', 'البحث عن علامة', 'Application Search');


INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'APPLICATION_SEARCH_ATTACHMENTS', 'المرفقات التى تم تقديمها لتدعيم الطلب', 'Attachments ?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'APPLICATION_SEARCH_INFO', 'هل بيانات مقدم الطلب كامله و واضحه ؟', 'Is applicant information clear ?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

