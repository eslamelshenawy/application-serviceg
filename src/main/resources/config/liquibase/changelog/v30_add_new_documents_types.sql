INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES (93, N'Waiver Document', N'WAIVER_DOCUMENT', N'وثيقة التنازل', N'وثيقة التنازل', N'OWNERSHIP_CHANGE_REQUEST', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES (94, N'Legal Document To Prove Ownership Transfer', N'LEGAL_DOCUMENT_TO_PROVE_OWNERSHIP_TRANSFER', N'الوثيقة القانونية لاثبات نقل الملكية', N'الوثيقة القانونية لاثبات نقل الملكية', N'OWNERSHIP_CHANGE_REQUEST', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES (95, N'Ownership Change POA', N'OWNERSHIP_CHANGE_POA', N'وثيقة التفويض', N'وثيقة التفويض', N'OWNERSHIP_CHANGE_REQUEST', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES (96, N'IB Document (Madrid System mm5)', N'IB_DOCUMENT(MADRID_SYSTEM_MM5)', N'وثيقة المكتب الدولي (نظام مدريد mm5)', N'وثيقة المكتب الدولي (نظام مدريد mm5)', N'OWNERSHIP_CHANGE_REQUEST', NULL,0);

UPDATE application.lk_document_type
SET description = 'وثيقة الدعم'
WHERE id = 80;



