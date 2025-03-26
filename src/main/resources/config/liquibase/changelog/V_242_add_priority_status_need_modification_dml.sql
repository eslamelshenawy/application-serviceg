INSERT INTO application.lk_application_priority_status (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,ips_status_desc_ar,ips_status_desc_en,code) VALUES
    ((select (max(id) + 1) from application.lk_application_priority_status),NULL,NULL,NULL,NULL,0,'مطلوب التعديل','Need Mdification','NEED_MODIFICATION');
