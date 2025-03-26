INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_types), 'REVIEW_REQUEST_DOCUMENTS_AND_REPLY_OPP', 'طلب الاعتراض', 'Opposition Request');


INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'OPPOSITION_REQUEST_ATTACHMENTS', 'المرفقات التى تم تقديمها لتدعيم الطلب', 'Attachments ?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'OPPOSITION_REQUEST_INFO', 'هل بيانات مقدم الطلب كامله و واضحه ؟', 'Is applicant information clear ?', 'BOOLEAN');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

update application.lk_support_services
set desc_ar='خدمة تتيح لكل ذي شأن تقديم الاعتراض علي تسجيل علامة تجارية خلال فترة نشرها صحيفة الملكية الفكرية'
where code = 'OPPOSITION_REQUEST';