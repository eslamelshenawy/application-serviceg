
create table application.support_service_users
(id int8 not null, created_by_user varchar(255), created_date timestamp,
modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null,
user_name varchar(255), user_role varchar(255),
application_support_service_type_id int8 not null, primary key (id));

alter table application.support_service_users add constraint FK5qaryr5uujauok13mlg7w91r
    foreign key (application_support_service_type_id) references
    application.application_support_services_type;