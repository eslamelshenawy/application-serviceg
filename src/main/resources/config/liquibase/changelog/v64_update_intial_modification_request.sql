--liquibase formatted sql
-- changeset application-service:v64_update_intial_modification_request.sql


-- delete invalid data
delete from application.initial_modification_request  o where o.application_support_services_type_id  is null;

-- update data
UPDATE application.initial_modification_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;


-- drop un-used columns
ALTER TABLE application.initial_modification_request DROP CONSTRAINT fkfmu6dcvwwlbargxci647pji66;
ALTER TABLE application.initial_modification_request DROP COLUMN created_by_user;
ALTER TABLE application.initial_modification_request DROP COLUMN created_date;
ALTER TABLE application.initial_modification_request DROP COLUMN modified_by_user;
ALTER TABLE application.initial_modification_request DROP COLUMN modified_date;
ALTER TABLE application.initial_modification_request DROP COLUMN is_deleted;
ALTER TABLE application.initial_modification_request DROP COLUMN application_support_services_type_id;


