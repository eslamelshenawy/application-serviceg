

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'ملف تغيير ملكية xml', NULL, 'OWNERSHIP_APPLICATION_XML', 'ملف طلب تغيير ملكية xml', 'OWNERSHIP_APPLICATION_XML');

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'ملف ترخيص الطلب xml', NULL, 'LICENSING_APPLICATION_XML', 'ملف ترخيص الطلب xml', 'LICENSING_APPLICATION_XML');
