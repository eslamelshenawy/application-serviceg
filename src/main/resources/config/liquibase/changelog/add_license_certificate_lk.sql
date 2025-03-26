INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES((select (max(id) + 1) from application.lk_certificate_types), 'LICENSE_REGISTRATION_CERTIFICATE', 'شهادة تسجيل ترخيص', 'License Registration Certificate');

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'License Registration Certificate', 'License Registration Certificate', 'شهادة تسجيل ترخيص', 'شهادة تسجيل ترخيص', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);

INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES((select (max(id) + 1) from application.lk_certificate_types), 'LICENSE_CANCELLATION_CERTIFICATE', 'شهادة الغاء ترخيص', 'License Cancellation Certificate');

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'License Cancellation Certificate', 'License Cancellation Certificate', 'شهادة الغاء ترخيص', 'شهادة الغاء ترخيص', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
