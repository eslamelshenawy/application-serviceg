--liquibase formatted sql
-- changeset application-service:V51_installment_add_notification_data_ddl.sql
alter table application.application_installment_notifications add column customer_id int8;
alter table application.application_installment_notifications add column email varchar(255);
alter table application.application_installment_notifications add column mobile varchar(255);
alter table application.application_installment_notifications add column exception_message text;
