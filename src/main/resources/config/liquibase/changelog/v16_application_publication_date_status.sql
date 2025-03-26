
--liquibase formatted sql
-- changeset application-service:v16_application_publication_date_status.sql

INSERT INTO application.lk_application_status (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,ips_status_desc_ar,ips_status_desc_en,code) VALUES
    (26,NULL,NULL,NULL,NULL,0,'بإنتظار عمليات التحقق','Awaiting Verifications','AWAITING_VERIFICATION');

INSERT INTO application.lk_application_status (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,ips_status_desc_ar,ips_status_desc_en,code) VALUES
    (27,NULL,NULL,NULL,NULL,0,'بإنتظار تعديل XML','Awaiting for update XML','AWAITING_FOR_UPDATE_XML');

ALTER TABLE application.applications_info ADD publication_date timestamp NULL;
