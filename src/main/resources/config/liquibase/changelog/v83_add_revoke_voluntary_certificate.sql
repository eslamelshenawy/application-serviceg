INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en)
VALUES((select (max(id) + 1) from application.lk_certificate_types), 'REVOKE_VOLUNTARY_TRADEMARK_CERTIFICATE', 'شهادة شطب علامة طواعية', 'Revoke Voluntary Trademark Certificate');

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Revoke Voluntary Trademark Certificate', 'Revoke Voluntary Trademark Certificate', 'خطاب شطب علامة طواعية', 'خطاب شطب علامة طواعية', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
