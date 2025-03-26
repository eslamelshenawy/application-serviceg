--liquibase formatted sql
-- changeset application-service:v44_add_installment_config.sql

-- create tables
create table application.application_installment_config_types (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, notification_template_type varchar(255), notification_type varchar(255), application_installment_config_id int8, primary key (id));
create table application.application_installments_config (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, application_category varchar(255), desertion_duration int4, grace_duration int4, installment_type varchar(255), last_running_date timestamp, notification_duration int4, payment_duration int4, payment_interval_years int4, primary key (id));
alter table application.application_installment_config_types add constraint FKlkn7l9428ycw6epwoe21hleri foreign key (application_installment_config_id) references application.application_installments_config;


-- insert config
INSERT INTO application.application_installments_config
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, application_category, desertion_duration, grace_duration, installment_type, last_running_date, notification_duration, payment_duration, payment_interval_years)
VALUES(1, NULL, NULL, NULL, NULL, 0, 'PATENT', 12, 3, 'ANNUAL', '2023-08-09 22:48:49.616', 3, 3, 1);
INSERT INTO application.application_installments_config
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, application_category, desertion_duration, grace_duration, installment_type, last_running_date, notification_duration, payment_duration, payment_interval_years)
VALUES(2, NULL, NULL, NULL, NULL, 0, 'INDUSTRIAL_DESIGN', 12, 3, 'INSTALLMENT', '2023-08-09 22:48:49.729', 3, 3, 5);
INSERT INTO application.application_installments_config
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, application_category, desertion_duration, grace_duration, installment_type, last_running_date, notification_duration, payment_duration, payment_interval_years)
VALUES(3, NULL, NULL, NULL, NULL, 0, 'TRADEMARK', 12, 3, 'INSTALLMENT', '2023-08-09 22:48:49.805', 3, 3, 10);


-- insert config types
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(1, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_SOON', 'DUE_DATE_SOON', 1);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(2, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_NOW', 'DUE_DATE', 1);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(3, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_OVER', 'DUE_DATE_EXCEEDED', 1);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(4, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DESERTED', 'FINAL_REJECTION', 1);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(5, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_SOON', 'DUE_DATE_SOON', 2);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(6, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_NOW', 'DUE_DATE', 2);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(7, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_OVER', 'DUE_DATE_EXCEEDED', 2);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(8, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DESERTED', 'FINAL_REJECTION', 2);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(9, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_SOON', 'DUE_DATE_SOON', 3);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(10, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_NOW', 'DUE_DATE', 3);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(11, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DUE_DATE_OVER', 'DUE_DATE_EXCEEDED', 3);
INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(12, NULL, NULL, NULL, NULL, 0, 'INSTALLMENT_DESERTED', 'FINAL_REJECTION', 3);

