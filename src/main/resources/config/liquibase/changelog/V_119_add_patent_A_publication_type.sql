INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'PUBLICATION_A', 'نشر ا', 'publication A', 1);
