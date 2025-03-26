--liquibase formatted sql
-- changeset application-service:v67_update_licensing_request.sql


-- delete invalid data
delete from application.licence_request  o where o.application_support_services_type_id  is null;


-- update data
UPDATE application.licence_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;



-- drop un-used columns
ALTER TABLE application.licence_request DROP COLUMN created_by_user;
ALTER TABLE application.licence_request DROP COLUMN created_date;
ALTER TABLE application.licence_request DROP COLUMN modified_by_user;
ALTER TABLE application.licence_request DROP COLUMN modified_date;
ALTER TABLE application.licence_request DROP COLUMN is_deleted;
ALTER TABLE application.licence_request DROP CONSTRAINT fk140sltif88op8gfqt4dv1gu7o;
ALTER TABLE application.licence_request DROP COLUMN application_support_services_type_id;

