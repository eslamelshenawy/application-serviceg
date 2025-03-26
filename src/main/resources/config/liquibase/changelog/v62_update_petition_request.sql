--liquibase formatted sql
-- changeset application-service:v62_update_petition_request.sql


-- delete invalid data
delete from application.petition_recovery_request  o where o.application_support_services_type_id  is null;


-- update data

UPDATE application.petition_recovery_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;


-- drop un-used columns
ALTER TABLE application.petition_recovery_request DROP CONSTRAINT fklfv462ql2194twx0yu3vi8pk;
ALTER TABLE application.petition_recovery_request DROP COLUMN created_by_user;
ALTER TABLE application.petition_recovery_request DROP COLUMN created_date;
ALTER TABLE application.petition_recovery_request DROP COLUMN modified_by_user;
ALTER TABLE application.petition_recovery_request DROP COLUMN modified_date;
ALTER TABLE application.petition_recovery_request DROP COLUMN is_deleted;
ALTER TABLE application.petition_recovery_request DROP COLUMN application_support_services_type_id;
