--liquibase formatted sql
-- changeset application-service:v55_add_support_service_request_number.sql
alter table application.application_support_services_type add column request_number varchar(255);
alter table application.application_support_services_type add constraint UK_1m423fcv6bx7bxik9l627bjsd unique (request_number);

ALTER TABLE application.appeal_request DROP COLUMN appeal_request_number;
ALTER TABLE application.opposition DROP COLUMN opposition_number;


UPDATE application.application_support_services_type
SET request_number  = 'SS-' || LPAD(number::text, 6, '0')
    FROM (
    SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS number
    FROM application.application_support_services_type
    order by id asc
) AS numbered_rows
WHERE application.application_support_services_type.id = numbered_rows.id and (request_number is null or request_number = '');