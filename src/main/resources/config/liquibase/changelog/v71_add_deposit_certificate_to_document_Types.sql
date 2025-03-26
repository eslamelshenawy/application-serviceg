INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES((select (max(id) + 1) from application.lk_certificate_types), 'DEPOSIT_CERTIFICATE', 'شهادة ايداع', 'Deposit Certificate');

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Deposit Certificate', 'Deposit Certificate', 'شهادة ايداع', 'شهادة ايداع', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);


