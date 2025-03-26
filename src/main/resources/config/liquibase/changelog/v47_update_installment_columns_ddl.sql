--liquibase formatted sql
-- changeset application-service:v47_update_installment_columns_ddl.sql

ALTER TABLE application.application_installment_notifications DROP COLUMN last_notification;
alter table application.application_installments add column installment_index int4 not null default 0;
