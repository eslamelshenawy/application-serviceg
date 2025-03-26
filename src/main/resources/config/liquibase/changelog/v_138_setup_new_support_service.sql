-- lk_support_services
INSERT INTO application.lk_support_services (id,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code)
    VALUES ((select (max(id) + 1) from application.lk_support_services),0,0,'طلب التماس تصحيح أو إضافة أسبقية','Priority Modification or Addition Request','طلب التماس تصحيح أو إضافة أسبقية','Priority Modification or Addition Request','PATENT_PRIORITY_REQUEST');
INSERT INTO application.lk_support_services (id,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code)
    VALUES ((select (max(id) + 1) from application.lk_support_services),0,0,'إضافة أو تعديل ألأسبقية','Add or Edit Priority','إضافة أو تعديل ألأسبقية','Add or Edit Priority','PATENT_PRIORITY_MODIFY');
--lk_support_service_type
INSERT INTO application.lk_support_service_type (id,is_deleted,desc_ar,desc_en,"type",code,is_free)
    VALUES ((select (max(id) + 1) from application.lk_support_service_type),0,'طلب التماس تصحيح أو إضافة أسبقية','Priority Modification or Addition Request','INITIAL_MODIFICATION','PATENT_PRIORITY_REQUEST',false);
INSERT INTO application.lk_support_service_type (id,is_deleted,desc_ar,desc_en,"type",code,is_free)
    VALUES ((select (max(id) + 1) from application.lk_support_service_type),0,'إضافة أو تعديل ألأسبقية','Add or Edit Priority','INITIAL_MODIFICATION','PATENT_PRIORITY_MODIFY',false);
-- support_service_application_categories
INSERT INTO application.support_service_application_categories (support_service_id , category_id)
    VALUES((select id from application.lk_support_services WHERE code = 'PATENT_PRIORITY_REQUEST'), 1);
INSERT INTO application.support_service_application_categories (support_service_id , category_id)
    VALUES((select id from application.lk_support_services WHERE code = 'PATENT_PRIORITY_MODIFY'), 1);
