--liquibase formatted sql
-- changeset application-service:v58_update_agent_requests.sql

-- delete invalid data
delete from application.agent_substitution_request  o where o.application_support_services_type_id  is null;


-- update data
UPDATE application.agent_substitution_request  o SET id  = o.application_support_services_type_id  WHERE o.application_support_services_type_id is not null ;


-- drop un-used columns
ALTER TABLE application.agent_substitution_request DROP CONSTRAINT fkea9r7ap7bkrhoqht062u1f11s;
ALTER TABLE application.agent_substitution_request DROP COLUMN created_by_user;
ALTER TABLE application.agent_substitution_request DROP COLUMN created_date;
ALTER TABLE application.agent_substitution_request DROP COLUMN modified_by_user;
ALTER TABLE application.agent_substitution_request DROP COLUMN modified_date;
ALTER TABLE application.agent_substitution_request DROP COLUMN is_deleted;
ALTER TABLE application.agent_substitution_request DROP COLUMN application_support_services_type_id;
