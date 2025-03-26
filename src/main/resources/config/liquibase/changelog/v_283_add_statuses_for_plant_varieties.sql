INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مسودة', 'Draft', 'DRAFT', 'مسودة', 'Draft', 3);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'جديد', 'New', 'NEW', 'جديد', 'New', 3);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم تقديم الطلب', 'Waiting for payment of the application fee', 'WAITING_FOR_APPLICATION_FEE_PAYMENT', 'بانتظار سداد رسوم تقديم الطلب', 'Pay the application submission fee', 3);
