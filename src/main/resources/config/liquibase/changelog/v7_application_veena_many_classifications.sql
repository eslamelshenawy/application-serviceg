--liquibase formatted sql
-- changeset application-service:v7_application_veena_many_classifications.sql


create table application.application_veena_classifications (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, application_id int8, veena_assistant_department_id int8, veena_classification_id int8, veena_department_id int8, primary key (id));
alter table application.application_veena_classifications add constraint FK2b7fr6ffu9ykps5ic9uj33qps foreign key (application_id) references application.applications_info;
alter table application.application_veena_classifications add constraint FK5oc2ri5byqtuyeewar0jfa620 foreign key (veena_assistant_department_id) references application.lk_veena_assistant_department;
alter table application.application_veena_classifications add constraint FKiu603485kgup9si10yvd8t6ts foreign key (veena_classification_id) references application.lk_veena_classification;
alter table application.application_veena_classifications add constraint FK4nc5fu325lfod1t7bd3li1smh foreign key (veena_department_id) references application.lk_veena_department;


ALTER TABLE application.application_veena_classifications ADD CONSTRAINT app_class_dept_asssistant_uniqu UNIQUE (application_id,veena_assistant_department_id,veena_classification_id,veena_department_id);

ALTER TABLE application.applications_info DROP CONSTRAINT veena_assistant_department_fk;
ALTER TABLE application.applications_info DROP CONSTRAINT veena_classification_fk;
ALTER TABLE application.applications_info DROP CONSTRAINT veena_department_id_fk;
ALTER TABLE application.applications_info DROP COLUMN veena_assistant_department_id;
ALTER TABLE application.applications_info DROP COLUMN veena_classification_id;
ALTER TABLE application.applications_info DROP COLUMN veena_department_id;
