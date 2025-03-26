--liquibase formatted sql
-- changeset application-service:v61_update_eviction_request.sql


-- delete invalid data
delete from application.eviction_request  o where o.application_support_services_type_id  is null;


-- update data

UPDATE application.eviction_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;

-- drop un-used columns
ALTER TABLE application.eviction_request DROP CONSTRAINT fkcfa2d44fvscff0uq0jn7593gu;
ALTER TABLE application.eviction_request DROP COLUMN created_by_user;
ALTER TABLE application.eviction_request DROP COLUMN created_date;
ALTER TABLE application.eviction_request DROP COLUMN modified_by_user;
ALTER TABLE application.eviction_request DROP COLUMN modified_date;
ALTER TABLE application.eviction_request DROP COLUMN is_deleted;
ALTER TABLE application.eviction_request DROP COLUMN application_support_services_type_id;
