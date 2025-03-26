INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_types), 'LOG_DECISION_COORDINATOR_REVIEW_REQUEST_TMAPPREQ', 'طلب تظلم', 'complainant request');

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'VERIFIED_AGENCY', 'هل تم التأكد من الوكالة المقدمة؟', 'Has the agency provided been verified?', 'INTEGER');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'WRITTEN_OPPOSITION', 'هل تم كتابة قرار الاعتراض؟ ', 'Was the opposition decision written?', 'INTEGER');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'OPPOSITION_REQUEST_INFO', 'هل تم التأكد من كافه البيانات الكامله لمقدم التظلم ؟  ', 'Have all the complete information of the person submitting the grievance been verified?', 'INTEGER');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'OPPOSITION_PAYMENT', 'هل تم التأكد من سداد رسوم التظلم ؟  ', 'Has it been confirmed that the grievance fees have been paid?', 'INTEGER');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'OPPOSITION_DEADLINE', 'هل تم التأكد من المهل النظاميه للتظلم 60 يوم ؟', 'Has the 60-day deadline for filing a grievance been confirmed?', 'INTEGER');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));

INSERT INTO application.lk_task_eqm_items (id, code, name_ar, name_en, rating_value_type)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_items), 'COMMITTEE_DECISION', 'هل تم التأكد من وصول قرار اللجنه للمتظلم ؟', 'Has it been confirmed that the committee’s decision has reached the complainant?', 'INTEGER');

INSERT INTO application.task_eqm_type_items (lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES ((SELECT max(id) FROM application.lk_task_eqm_types), (SELECT max(id) FROM application.lk_task_eqm_items));