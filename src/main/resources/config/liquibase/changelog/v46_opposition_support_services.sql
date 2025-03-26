--liquibase formatted sql
-- changeset application-service:v46_opposition_support_services.sql

alter table application.opposition add column application_support_services_type_id int8;
alter table application.opposition add constraint FKnxp9fcibqx8u3c34o9smkarpo foreign key (application_support_services_type_id) references application.application_support_services_type;
