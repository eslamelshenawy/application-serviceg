INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'PCT Application Copy document', 'PCT Application Copy document', 'نسخة من الطلب الدولي', 'نسخة من الطلب الدولي', 'APPLICATION', NULL,0);

ALTER TABLE application.pct ADD COLUMN pct_copy_document_id int8 NULL;

ALTER TABLE application.pct ADD CONSTRAINT fk_pct_copy_document_id FOREIGN KEY (pct_copy_document_id) REFERENCES application.documents(id);
