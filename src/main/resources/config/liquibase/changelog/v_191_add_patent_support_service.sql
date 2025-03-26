INSERT INTO application.support_service_application_categories
(category_id,
 support_service_id)
VALUES ((select id from application.lk_application_category lac where lac.saip_code = 'PATENT'),
        (select id from application.lk_support_services lss where lss.code = 'AGENT_SUBSTITUTION' limit 1));