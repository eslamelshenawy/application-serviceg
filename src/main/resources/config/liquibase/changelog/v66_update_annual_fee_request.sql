--liquibase formatted sql
-- changeset application-service:v66_update_annual_fee_request.sql


-- delete invalid data
delete from application.annual_fees_request  o where o.application_support_services_type_id  is null;


-- update data
UPDATE application.annual_fees_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;



-- drop un-used columns
ALTER TABLE application.annual_fees_request DROP COLUMN created_by_user;
ALTER TABLE application.annual_fees_request DROP COLUMN created_date;
ALTER TABLE application.annual_fees_request DROP COLUMN modified_by_user;
ALTER TABLE application.annual_fees_request DROP COLUMN modified_date;
ALTER TABLE application.annual_fees_request DROP COLUMN is_deleted;
ALTER TABLE application.annual_fees_request DROP COLUMN request_number;
ALTER TABLE application.annual_fees_request DROP CONSTRAINT fkn8n06817l77515ogpmqqb3kwn;
ALTER TABLE application.annual_fees_request DROP COLUMN application_support_services_type_id;


