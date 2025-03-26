update application.lk_support_services set
name_ar = 'البحث عن علامة تجارية',
desc_ar = 'البحث عن علامة تجارية'
where code = 'APPLICATION_SEARCH';

insert into application.support_service_application_categories (support_service_id , category_id)
values(19, 5);



