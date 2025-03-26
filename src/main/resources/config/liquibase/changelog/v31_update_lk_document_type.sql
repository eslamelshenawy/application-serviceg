
delete from application.lk_document_type where code = 'APPLICATION_XML';

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES(92, NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'ملف ال xml', NULL, 'APPLICATION_XML', 'ملف الطلب xml', 'APPLICATION_XML');
