--liquibase formatted sql
-- changeset application-service:application_accelerated_ddl.sql
alter table application.application_accelerated add column if not exists refused boolean default false;