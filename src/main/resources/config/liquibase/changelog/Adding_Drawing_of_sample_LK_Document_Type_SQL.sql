--liquibase formatted sql
-- changeset application-service:Adding_Drawing_of_sample_LK_Document_Type_SQL.sql

INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (59, N'Drawing of the sample', N'رسمة النموذج', N'APPLICATION', NULL,0);
