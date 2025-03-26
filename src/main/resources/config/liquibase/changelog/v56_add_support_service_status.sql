--liquibase formatted sql
-- changeset application-service:v56_add_support_service_status.sql

-- create the table
CREATE TABLE application.lk_support_service_request_status (
       id int4 NOT NULL,
       code varchar(255) NULL,
       name_ar varchar(255) NULL,
       name_en varchar(255) NULL,
       CONSTRAINT lk_support_service_request_status_pkey PRIMARY KEY (id)
);

-- add to status to support service type table
alter table application.application_support_services_type add column request_status int4;
alter table application.application_support_services_type add constraint FKdruvf80big3a4om6m6xyl0ivt foreign key (request_status) references application.lk_support_service_request_status;

-- insert status list
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES(1, 'DRAFT', 'مسوده', 'Draft');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES(2, 'UNDER_PROCEDURE', 'تحت الاجراء', 'Under Procedure');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES(3, 'REQUEST_CORRECTION', 'دعوه للتصحيح', 'Request Correction');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES(4, 'APPROVED', 'مقبول', 'Approved');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES(5, 'REJECTED', 'مرفوض', 'Rejected');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES(6, 'COMPLETED', 'مكتمل', 'Completed');

-- rename payment status
ALTER TABLE application.application_support_services_type RENAME COLUMN application_request_status TO payment_status;


-- migrate data

-- delete invalid data
delete from application.opposition_documents d where d.opposition_id  in (select o.id from application.opposition o where o.application_support_services_type_id is null);
delete from application.opposition_classification  where opposition_id  in (select o.id from application.opposition o where o.application_support_services_type_id is null);
delete from application.opposition o where o.application_support_services_type_id is null;

-- drop constrains
ALTER TABLE application.opposition_documents DROP CONSTRAINT fki5j09lqel04yaf6qnxqes4gxm;
ALTER TABLE application.opposition_classification DROP CONSTRAINT fkqot3ao20pp7q2xp9fsip0kkxo;
ALTER TABLE application.opposition_documents DROP CONSTRAINT ukkd58yr24yruty7fcbmcufktwn;
ALTER TABLE application.opposition_classification DROP CONSTRAINT uktgh18vih0ts0at426la0076jb;
ALTER TABLE application.opposition DROP CONSTRAINT opposition_pkey;

-- migrate data
update application.opposition_documents d set opposition_id = (select o.application_support_services_type_id  from application.opposition o where o.id = d.opposition_id );

update application.opposition_classification d set opposition_id  = (select o.application_support_services_type_id  from application.opposition o where o.id = d.opposition_id );

UPDATE application.opposition o SET id  = o.application_support_services_type_id ;

-- restore constrains
ALTER TABLE application.opposition ADD CONSTRAINT opposition_pkey PRIMARY KEY (id);
ALTER TABLE application.opposition_documents ADD CONSTRAINT fki5j09lqel04yaf6qnxqes4gxm FOREIGN KEY (opposition_id) REFERENCES application.opposition(id);
ALTER TABLE application.opposition_classification ADD CONSTRAINT fkqot3ao20pp7q2xp9fsip0kkxo FOREIGN KEY (opposition_id) REFERENCES application.opposition(id);
ALTER TABLE application.opposition_documents ADD CONSTRAINT ukkd58yr24yruty7fcbmcufktwn UNIQUE (opposition_id, document_id);
ALTER TABLE application.opposition_classification ADD CONSTRAINT uktgh18vih0ts0at426la0076jb UNIQUE (opposition_id, classification_id);


-- drop un-used columns
ALTER TABLE application.opposition DROP CONSTRAINT fknxp9fcibqx8u3c34o9smkarpo;
ALTER TABLE application.opposition DROP COLUMN created_by_user;
ALTER TABLE application.opposition DROP COLUMN created_date;
ALTER TABLE application.opposition DROP COLUMN modified_by_user;
ALTER TABLE application.opposition DROP COLUMN modified_date;
ALTER TABLE application.opposition DROP COLUMN is_deleted;
ALTER TABLE application.opposition DROP COLUMN application_support_services_type_id;

