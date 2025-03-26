-- Auto-generated SQL script #202406121221
INSERT INTO application.lk_support_service_request_status (id,code,name_ar,name_en,name_ar_external,name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status),'ACCEPTED_AGENCY_REQUEST','قبول طلب تعيين وكيل',' Accepted','قبول طلب تعيين وكيل','Accepted');
