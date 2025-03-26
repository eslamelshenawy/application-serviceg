
delete from application.support_service_application_categories  c where
c.support_service_id in (select id from application.lk_support_services where code='AGENT_SUBSTITUTION')
and
C.category_id = (select id from application.lk_application_category where saip_code = 'PATENT');


delete from application.support_service_application_categories  c where
c.support_service_id in (select id from application.lk_support_services where code='AGENT_SUBSTITUTION')
and
C.category_id = (select id from application.lk_application_category where saip_code = 'TRADEMARK');



delete from application.support_service_application_categories  c where
c.support_service_id in (select id from application.lk_support_services where code='AGENT_SUBSTITUTION')
and
C.category_id = (select id from application.lk_application_category where saip_code = 'INDUSTRIAL_DESIGN');
