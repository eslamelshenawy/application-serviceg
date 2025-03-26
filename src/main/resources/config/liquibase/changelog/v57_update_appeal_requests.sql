--liquibase formatted sql
-- changeset application-service:v57_update_appeal_requests.sql

-- delete invalid data
delete from application.appeal_committee_opinion  d where d.appeal_request_id  in (select o.id from application.appeal_request o where o.application_support_services_type_id  is null);
delete from application.appeal_request_documents d where d.appeal_request_id  in (select o.id from application.appeal_request o where o.application_support_services_type_id  is null);
delete from application.appeal_request  o where o.application_support_services_type_id  is null;

-- drop constrains
ALTER TABLE application.appeal_committee_opinion DROP CONSTRAINT fkop6bpb6fk2giodf3us7jy1m9w;
ALTER TABLE application.appeal_request_documents DROP CONSTRAINT fkdvo4rhvb7xy326jffwfiladrb;
ALTER TABLE application.appeal_request DROP CONSTRAINT appeal_request_pkey;

-- update data
update application.appeal_request_documents  d set appeal_request_id  = (select o.application_support_services_type_id  from application.appeal_request  o where o.id = d.appeal_request_id);

update application.appeal_committee_opinion  d set appeal_request_id  = (select o.application_support_services_type_id  from application.appeal_request  o where o.id = d.appeal_request_id );

UPDATE application.appeal_request  o SET id  = o.application_support_services_type_id  WHERE o.application_support_services_type_id is not null ;


-- restore constrains
ALTER TABLE application.appeal_request ADD CONSTRAINT appeal_request_pkey PRIMARY KEY (id);
ALTER TABLE application.appeal_committee_opinion ADD CONSTRAINT fkop6bpb6fk2giodf3us7jy1m9w FOREIGN KEY (appeal_request_id) REFERENCES application.appeal_request(id);
ALTER TABLE application.appeal_request_documents ADD CONSTRAINT fkdvo4rhvb7xy326jffwfiladrb FOREIGN KEY (appeal_request_id) REFERENCES application.appeal_request(id);

-- drop un-used columns
ALTER TABLE application.appeal_request DROP CONSTRAINT fkjmubfqo77tfwbh9fo5na1ahvl;
ALTER TABLE application.appeal_request DROP COLUMN created_by_user;
ALTER TABLE application.appeal_request DROP COLUMN created_date;
ALTER TABLE application.appeal_request DROP COLUMN modified_by_user;
ALTER TABLE application.appeal_request DROP COLUMN modified_date;
ALTER TABLE application.appeal_request DROP COLUMN is_deleted;
ALTER TABLE application.appeal_request DROP COLUMN application_support_services_type_id;
