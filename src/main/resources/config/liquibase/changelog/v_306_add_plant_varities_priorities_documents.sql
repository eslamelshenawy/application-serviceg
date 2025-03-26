INSERT INTO application.lk_application_status (id,created_by_user,is_deleted,ips_status_desc_ar,ips_status_desc_en,code,ips_status_desc_ar_external,ips_status_desc_en_external,application_category_id)
VALUES ((select max(id) + 1 from application.lk_application_status),'1',0,'بانتظار مستندات الأسبقية','Waiting for priority documents','PV_WAITING_DOCUMENTS_ATTACHMENT','بانتظار مستندات الأسبقية','Waiting for priority documents',
        (select id FROM application.lk_application_category x
         WHERE x.saip_code = 'PLANT_VARIETIES'));