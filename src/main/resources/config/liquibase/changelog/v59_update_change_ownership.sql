--liquibase formatted sql
-- changeset application-service:v59_update_change_ownership.sql

-- delete invalid data
delete from application.change_ownership_customers ow where ow.change_ownership_request_id in (select id from application.change_ownership_request o where o.application_support_services_type_id is null );
delete from application.change_ownership_request  o where o.application_support_services_type_id  is null;

-- drop constrains
ALTER TABLE application.change_ownership_customers DROP CONSTRAINT fkk1la9m2jpu5klt1adxg4mxydx;


-- update data
update application.change_ownership_customers d set change_ownership_request_id  = (select o.application_support_services_type_id  from application.change_ownership_request  o where o.id = d.change_ownership_request_id  );

UPDATE application.change_ownership_request  SET id  = application_support_services_type_id  WHERE application_support_services_type_id is not null ;

-- restore constrains
ALTER TABLE application.change_ownership_customers ADD CONSTRAINT fkk1la9m2jpu5klt1adxg4mxydx FOREIGN KEY (change_ownership_request_id) REFERENCES application.change_ownership_request(id);


-- drop un-used columns
ALTER TABLE application.change_ownership_request DROP CONSTRAINT fk5fty8oad4y9ex6p3x1n2fqvk6;
ALTER TABLE application.change_ownership_request DROP COLUMN created_by_user;
ALTER TABLE application.change_ownership_request DROP COLUMN created_date;
ALTER TABLE application.change_ownership_request DROP COLUMN modified_by_user;
ALTER TABLE application.change_ownership_request DROP COLUMN modified_date;
ALTER TABLE application.change_ownership_request DROP COLUMN is_deleted;
ALTER TABLE application.change_ownership_request DROP COLUMN application_support_services_type_id;

