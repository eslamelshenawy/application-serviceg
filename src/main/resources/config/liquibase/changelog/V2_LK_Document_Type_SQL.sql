--liquibase formatted sql
-- changeset application-service:LK_Document_Type_SQL.sql

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (60, N'Eviction Description Document', N'Eviction Description Document', N'وثيقة  وصف التخلي', N'وثيقة  وصف التخلي', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (61, N'Recovery Document', N'Recovery Document', N'وثيقة الاستعادة', N'وثيقة الاستعادة', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted) VALUES (62, N'Retraction reason document', N'Retraction reason document', N'وثيقة سبب الانسحاب', N'وثيقة سبب الانسحاب', N'APPLICATION', NULL,0);
