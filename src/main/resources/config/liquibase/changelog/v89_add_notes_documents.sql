--liquibase formatted sql
-- changeset application-service:v89_add_notes_documents.sql

INSERT INTO application.lk_document_type (id,is_deleted,category,description,"name",name_ar,code)
VALUES (113,0,'APPLICATION','مستندات ملاحظات المدقق الموضوعي','substantive_auditor_notes_documents','مستندات ملاحظات المدقق الموضوعي','SUBSTANTIVE_AUDITOR_NOTES_DOCUMENTS');



