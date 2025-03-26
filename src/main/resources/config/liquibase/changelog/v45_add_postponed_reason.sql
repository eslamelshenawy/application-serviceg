--liquibase formatted sql
-- changeset application-service:v45_add_postponed_reason.sql

ALTER TABLE application.application_installments DROP COLUMN postponed_reason;

alter table application.application_installments add column postponed_reason_id int8;
alter table application.application_installments add constraint FKqby3xaqjebs84wdckq9pf9f64 foreign key (postponed_reason_id) references application.lk_post_request_reasons;


