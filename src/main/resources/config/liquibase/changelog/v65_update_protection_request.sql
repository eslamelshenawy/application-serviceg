--liquibase formatted sql
-- changeset application-service:v65_update_protection_request.sql


-- delete invalid data
delete from application.protection_extend_request  o where o.application_support_services_type_id  is null;


-- update data
UPDATE application.protection_extend_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;



-- drop un-used columns
ALTER TABLE application.protection_extend_request DROP CONSTRAINT fkftk719jo15npy3eqpfwj43j5e;
ALTER TABLE application.protection_extend_request DROP COLUMN created_by_user;
ALTER TABLE application.protection_extend_request DROP COLUMN created_date;
ALTER TABLE application.protection_extend_request DROP COLUMN modified_by_user;
ALTER TABLE application.protection_extend_request DROP COLUMN modified_date;
ALTER TABLE application.protection_extend_request DROP COLUMN is_deleted;
ALTER TABLE application.protection_extend_request DROP COLUMN application_support_services_type_id;
