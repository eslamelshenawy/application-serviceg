INSERT INTO application.lk_application_category (id,saip_code,abbreviation,is_deleted,application_category_desc_ar,application_category_desc_en,application_category_is_active)
VALUES ((select max(id)+1 from application.lk_application_category),'GENERAL','GE',0,'عام','General',false);
