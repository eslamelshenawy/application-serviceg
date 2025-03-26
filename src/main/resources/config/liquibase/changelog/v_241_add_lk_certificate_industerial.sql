INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en, enabled)
VALUES((select max(id) + 1 from application.lk_certificate_types), 'INDUSTRIAL_ISSUE_CERTIFICATE', 'شهادة نموذج صناعي', 'Industrial issue certificate', true);

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id) + 1 from application.lk_document_type ), NULL, NULL, NULL, NULL, 0, 'IndustrialIssueCertificate', 'شهادة نموج صناعي', NULL, 'Industrial Issue Certificate', 'شهادة نموج صناعي', 'IndustrialIssueCertificate');