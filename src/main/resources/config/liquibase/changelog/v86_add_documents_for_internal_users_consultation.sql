INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'examinerConsultation-doc', 'CONSULTATION_DOC', 'وثيقة طلب استشارة', 'وثيقة طلب استشارة', 'APPLICATION', NULL,0);



INSERT INTO application.lk_application_status (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,ips_status_desc_ar,ips_status_desc_en,code) VALUES
    ((select (max(id) + 1) from application.lk_application_status) ,NULL,NULL,NULL,NULL,0,'ارسال تعديل الى مقدم الطلب','Sending updates to applicant', 'APPLICANT_SENDING_UPDATES');