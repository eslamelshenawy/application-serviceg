--liquibase formatted sql
-- changeset application-service:V1_LK_Document_Type_SQL.sql







ALTER TABLE application.lk_document_type ADD name_Ar varchar(255);
ALTER TABLE application.lk_document_type ADD  code varchar(255);



update application.lk_document_type set code = "name";
update application.lk_document_type set name_Ar = description;

