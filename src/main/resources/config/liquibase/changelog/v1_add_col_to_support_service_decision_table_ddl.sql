
ALTER TABLE application.support_services_type_decisions
    ADD COLUMN document_id int8 NULL,
ADD CONSTRAINT fk_document_id
    FOREIGN KEY (document_id)
    REFERENCES application.documents(id);


INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ( (select (max(id) + 1) from application.lk_document_type), 'support service decision'
       , 'SUPPORT_SERVICE_DECISION ', 'مستند القرار في الخدمة المساندة'
       , 'مستند القرار في الخدمة المساندة', 'SUPPORT_SERVICE', NULL, 0);
