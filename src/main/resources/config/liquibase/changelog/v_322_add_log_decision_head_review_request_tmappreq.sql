INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ', 'هل تم ارفاق المستندات القانونية بصورة صحيحة؟', 'Is legal documents is attached correctly', 'BOOLEAN');
