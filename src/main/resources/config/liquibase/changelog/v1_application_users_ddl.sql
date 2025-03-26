

create table application.application_users (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, user_name varchar(255), user_role varchar(255), application_id int8 not null, primary key (id));

alter table application.application_users add constraint FK5nx85qj7yubvx1wb8sojcnd7c foreign key (application_id) references application.applications_info;

