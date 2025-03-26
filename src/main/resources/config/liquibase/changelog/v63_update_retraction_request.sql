--liquibase formatted sql
-- changeset application-service:v63_update_retraction_request.sql


-- delete invalid data
delete from application.retraction_request  o where o.application_support_services_type_id  is null;


-- update data

UPDATE application.retraction_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;


-- drop un-used columns
ALTER TABLE application.retraction_request DROP CONSTRAINT fkcxqdw6wlb6r7fprju5j5v19p3;
ALTER TABLE application.retraction_request DROP COLUMN created_by_user;
ALTER TABLE application.retraction_request DROP COLUMN created_date;
ALTER TABLE application.retraction_request DROP COLUMN modified_by_user;
ALTER TABLE application.retraction_request DROP COLUMN modified_date;
ALTER TABLE application.retraction_request DROP COLUMN is_deleted;
ALTER TABLE application.retraction_request DROP COLUMN application_support_services_type_id;

