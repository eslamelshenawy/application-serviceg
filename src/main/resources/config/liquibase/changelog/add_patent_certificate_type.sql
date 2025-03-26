INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type),
        'PatentIssueCertificate', 'PATENT_ISSUE_CERTIFICATE', 'شهادة وثيقة حماية', 'شهادة وثيقة حماية', 'PATENT_ISSUE_CERTIFICATE', NULL,0);
