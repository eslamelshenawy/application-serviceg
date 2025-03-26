--liquibase formatted sql
-- changeset application-service:LK_Document_Type_SQL.sql

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (63, N'Evidence Document', N'Evidence Document', N'وثيقة الدليل', N'وثيقة الدليل', N'OPPOSITION', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (64, N'Opposition Document', N'Opposition Document', N'وثيقة لاعتراض', N'وثيقة لاعتراض', N'OPPOSITION', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (65, N'Opposition POA', N'Opposition POA', N'وثيقة التفويض', N'وثيقة التفويض', N'OPPOSITION', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (66, N'Opposition Reply Document', N'Opposition Reply Document', N'وثيقة الرد علي لاعتراض', N'وثيقة الرد علي لاعتراض', N'OPPOSITION', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (67, N'Opposition Waiver Document', N'Opposition Waiver Document', N'وثيقة التنازل', N'وثيقة التنازل', N'OPPOSITION', NULL,0);

