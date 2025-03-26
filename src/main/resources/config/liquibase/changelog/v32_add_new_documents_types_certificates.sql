INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Issue Certificate', 'ISSUE_CERTIFICATE', 'اصدار شهادة', 'اصدار شهادة', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Certified Register Copy', 'CERTIFIED_REGISTER_COPY  ', 'نسخة مصدقة من السجل', 'نسخة مصدقة من السجل', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Certified Priority Copy', 'CERTIFIED_PRIORITY_COPY', 'نسخة مصدقة من حقوق الأولوية', 'نسخة مصدقة من حقوق الأولوية', 'OWNERSHIP_CHANGE_REQUEST', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Proof Issuance Application', 'PROOF_ISSUANCE_APPLICATION', 'اثبات اصدار الطلب', 'اثبات اصدار الطلب', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Proof Facts Appeal', 'PROOF_FACTS_APPEAL', 'اثبات الوقائع بخصوص طلب التظلم', 'اثبات الوقائع بخصوص طلب التظلم', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Secret Design Document', 'SECRET_DESIGN_DOCUMENT', 'وثيقة اثبات التصميم السرى', 'وثيقة اثبات التصميم السرى', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Final Specification Document', 'FINAL_SPECIFICATION_DOCUMENT', 'وثيقة المواصفات النهائية', 'وثيقة المواصفات النهائية', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'All Application Records', 'ALL_APPLICATION_RECORDS ', 'جميع السجلات الخاصة بالطلب', 'جميع السجلات الخاصة بالطلب', 'DOCUMENTS_AND_CERTIFICATES_MANAGEMENT', NULL,0);

