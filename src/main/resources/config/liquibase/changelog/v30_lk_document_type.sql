INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES(92, NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'ملف ال xml', NULL, 'APPLICATION_XML ', 'ملف الطلب xml', 'APPLICATION_XML');

ALTER TABLE application.application_publication ADD COLUMN document_id int8 NULL;
ALTER TABLE application.application_publication ADD CONSTRAINT FK_APPLICATION_PUBLICATION_ON_DOCUMENT
    FOREIGN KEY (document_id) REFERENCES application.documents(id);
