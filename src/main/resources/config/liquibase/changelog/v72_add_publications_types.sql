INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'AGENT_SUBSTITUTION', 'تغيير وكيل', 'Agent Substitution', 1);

INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'AGENT_SUBSTITUTION', 'تغيير وكيل', 'Agent Substitution', 2);

INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'AGENT_SUBSTITUTION', 'تغيير وكيل', 'Agent Substitution', 5);
