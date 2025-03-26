INSERT INTO application.support_service_application_categories
    (support_service_id, category_id)
VALUES ((select id from application.lk_support_services where code = 'REVOKE_LICENCE_REQUEST'), (select id from application.lk_application_category where saip_code = 'TRADEMARK'));