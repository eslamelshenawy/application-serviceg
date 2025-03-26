update application.lk_support_services set is_deleted = 1
where code = 'AGENT_SUBSTITUTION';

insert into application.support_service_application_categories(support_service_id,category_id)
values((select min(id) from application.lk_support_services s where s.code = 'AGENT_SUBSTITUTION') ,(select id from application.lk_application_category c where c.saip_code  = 'PATENT'));


insert into application.support_service_application_categories(support_service_id,category_id)
values((select max(id) from application.lk_support_services s where s.code = 'AGENT_SUBSTITUTION') ,(select id from application.lk_application_category c where c.saip_code  = 'TRADEMARK'));

insert into application.support_service_application_categories(support_service_id,category_id)
values((select id from application.lk_support_services where code='AGENT_SUBSTITUTION' and id not in (select support_service_id from application.support_service_application_categories)) ,(select id from application.lk_application_category where saip_code = 'INDUSTRIAL_DESIGN'));


