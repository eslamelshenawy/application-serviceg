-- create bridge table to agent substitution
drop table if exists application.support_services_type_applications cascade;
create table application.support_services_type_applications (application_support_services_type_id int8 not null, application_id int8 not null);
alter table application.support_services_type_applications add constraint UKrwpy9oqgpw9iu8bvxglw55een unique (application_support_services_type_id, application_id);

alter table application.support_services_type_applications add constraint FK90bh8q5r4cn5lmh7v6he7nn0k foreign key (application_id) references application.applications_info;
alter table application.support_services_type_applications add constraint FKlh6b7evluhx9ww6x97e437hi3 foreign key (application_support_services_type_id) references application.application_support_services_type;

-- add columns
ALTER TABLE application.lk_support_service_type ADD "code" varchar(255) NULL;
alter table application.lk_support_service_type add column is_free boolean DEFAULT true;

-- insert add, substitute and delete agent services types
INSERT INTO application.lk_support_service_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, desc_ar, desc_en, "type", code)
VALUES((select max(id) + 1 from application.lk_support_service_type), NULL, NULL, NULL, NULL, 0, 'اضافه وكيل', 'Add Agent', 'AGENT_SUBSTITUTION', 'ADD_AGENT');
INSERT INTO application.lk_support_service_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, desc_ar, desc_en, "type", code)
VALUES((select max(id) + 1 from application.lk_support_service_type), NULL, NULL, NULL, NULL, 0, 'تبديل وكيل ', 'Agent Substitution', 'AGENT_SUBSTITUTION', 'SUBSTITUTE_AGENT');
INSERT INTO application.lk_support_service_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, desc_ar, desc_en, "type", code)
VALUES((select max(id) + 1 from application.lk_support_service_type), NULL, NULL, NULL, NULL, 0, 'حذف وكيل', 'Delete Agent', 'AGENT_SUBSTITUTION', 'DELETE_AGENT');

