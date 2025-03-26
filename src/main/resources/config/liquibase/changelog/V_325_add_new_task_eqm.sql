INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_types), 'LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ', 'طلب تظلم', 'Appeal Request');



INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type,shown)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ_TM_APPEAL_INFO', 'هل تم تقديم الطلب بصورة صحيحه ؟', 'Was the request information clear ?', 'BOOLEAN',true);
INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type,shown)
    VALUES ((SELECT MAX (id) + 1 FROM application.lk_task_eqm_items), 'LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ_TM_APPEAL_MODIFY_INFO', 'هل تم تعديل الطلب بصورة صحيحه ؟', 'Was the request modified correctly ?', 'BOOLEAN',true);

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
    VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ'),
            (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ_TM_APPEAL_INFO'));
INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
    VALUES ((SELECT id FROM application.lk_task_eqm_types WHERE CODE ='LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ'),
            (SELECT id FROM application.lk_task_eqm_items WHERE CODE = 'LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ_TM_APPEAL_MODIFY_INFO'));


