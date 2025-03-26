UPDATE application.lk_application_services
SET category_id = (select id from application.lk_application_category where saip_code='INTEGRATED_CIRCUITS')
WHERE code='INTEGRATED_CIRCUITS_REGISTRATION';