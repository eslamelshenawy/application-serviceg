-- Auto-generated SQL script #202409021014
INSERT INTO application.lk_support_service_request_status (id,code,name_ar,name_en,name_ar_external,name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ),'LICENSED_UPDATE','تم تعديل الترخيص ',' Licensed Update','تم تعديل الترخيص ',' Licensed Update');
INSERT INTO application.lk_support_service_request_status (id,code,name_ar,name_en,name_ar_external,name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ),'LICENSED_CANCELLATION','تم إلغاء الترخيص ',' Licensed Cancellation','تم إلغاء الترخيص ',' Licensed Cancellation');
