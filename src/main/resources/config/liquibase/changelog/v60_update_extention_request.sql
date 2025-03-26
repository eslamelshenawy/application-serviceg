--liquibase formatted sql
-- changeset application-service:v60_update_extention_request.sql


-- delete invalid data
delete from application.extension_request  o where o.application_support_services_type_id  is null;


-- update data

UPDATE application.extension_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;


-- drop un-used columns
ALTER TABLE application.extension_request DROP CONSTRAINT fkmfvcj9m29ywyjcnfvc110dm3h;
ALTER TABLE application.extension_request DROP COLUMN created_by_user;
ALTER TABLE application.extension_request DROP COLUMN created_date;
ALTER TABLE application.extension_request DROP COLUMN modified_by_user;
ALTER TABLE application.extension_request DROP COLUMN modified_date;
ALTER TABLE application.extension_request DROP COLUMN is_deleted;
ALTER TABLE application.extension_request DROP COLUMN application_support_services_type_id;
