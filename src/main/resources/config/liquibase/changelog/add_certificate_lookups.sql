INSERT INTO application.lk_certificate_types (id, code, name_ar, name_en)
VALUES (11, 'EXACT_COPY', 'نسخة طبق الاصل', 'Exact copy')
;

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order,
 "name", name_ar, code)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_document_type), NULL, NULL, NULL, NULL, 0,
        'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', 'صورة طبق الأصل', NULL, 'Exact Copy', 'صورة طبق الأصل', 'EXACT_COPY')
;

INSERT INTO application.certificate_types_application_categories
    (lk_certificate_type_id, lk_category_id)
VALUES (11, 5);
