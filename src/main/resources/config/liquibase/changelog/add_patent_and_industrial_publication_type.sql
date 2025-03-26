INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'LICENCE_CANCELLATION', 'الغاء الترخيص', 'licence cancellation', 1);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'PATENT_LICENCE_USE', 'ترخيص استخدام براءة اختراع', 'Patent Licence Use', 1);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'PATENT_REGISTRATION', 'تسجيل براءة اختراع', 'Patent Registration', 1);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'VOLUNTARY_PATENT_DELETION', 'شطب براءة اختراع طوعي', 'Voluntary Patent Deletion', 1);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'OWNERSHIP_TRANSFER', 'نقل ملكية', 'Ownership Transfer', 1);


INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'LICENCE_CANCELLATION', 'الغاء الترخيص', 'licence cancellation', 2);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'INDUSTRIAL_DESIGN_LICENCE_USE', 'ترخيص استخدام  نموذج صناعي', 'Industrial Design Licence Use', 2);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'INDUSTRIAL_DESIGN_REGISTRATION', 'تسجيل نموذج صناعي', 'Industrial Design Registration', 2);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'VOLUNTARY_INDUSTRIAL_DESIGN_DELETION', 'شطب نموذج صناعي طوعي', 'Voluntary Industrial Design Deletion', 2);
INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select (max(id) + 1) from application.lk_publication_type), 'OWNERSHIP_TRANSFER', 'نقل ملكية', 'Ownership Transfer', 2);
