--liquibase formatted sql
-- changeset application-service:v78_tm_agency_request_ddl.sql

CREATE TABLE application.lk_tm_agency_request_status (
    id int4 NOT NULL,
    code varchar(255) NULL,
    name_ar varchar(255) NULL,
    name_en varchar(255) NULL,
    CONSTRAINT lk_tm_agency_request_status_pkey PRIMARY KEY (id)
);

CREATE TABLE application.trademark_agency_requests (
   id int8 NOT NULL,
   created_by_user varchar(255) NULL,
   created_date timestamp NULL,
   modified_by_user varchar(255) NULL,
   modified_date timestamp NULL,
   is_deleted int4 NOT NULL,
   agency_checker_notes text NULL,
   agency_number varchar(255) NULL,
   agency_type varchar(255) NULL,
   agent_customer_code varchar(255) NULL,
   agent_expiry_date date NULL,
   agent_type_id int8 NULL,
   checker_username varchar(255) NULL,
   client_customer_code varchar(255) NULL,
   end_agency date NULL,
   agent_identity_number varchar(255) NULL,
   legal_agent_type varchar(255) NULL,
   organization_address varchar(255) NULL,
   organization_description text NULL,
   request_number varchar(255) NULL,
   start_agency date NULL,
   application_id int8 NULL,
   client_type_id int4 NULL,
   request_status_id int4 NULL,
   process_request_id int8 NULL,
   CONSTRAINT trademark_agency_requests_pkey PRIMARY KEY (id),
   CONSTRAINT uk_dfkeu0dj8j5y24lnako7b9n5f UNIQUE (request_number),
   CONSTRAINT fkf971k8us1djnhhwmyrofymbp8 FOREIGN KEY (client_type_id) REFERENCES application.lk_client_type(id),
   CONSTRAINT fkmap323gphtecot5655dtcwyij FOREIGN KEY (application_id) REFERENCES application.applications_info(id),
   CONSTRAINT fksbdik1aiahiblci18im76ixai FOREIGN KEY (request_status_id) REFERENCES application.lk_tm_agency_request_status(id)
);


CREATE TABLE application.tm_agency_requests_documents (
  agency_request_id int8 NOT NULL,
  document_id int8 NOT NULL,
  CONSTRAINT uk16ewkbcgrj1xd5vjjaf2d44de UNIQUE (agency_request_id, document_id),
  CONSTRAINT fkek8lw1pkgb4ng72xl695c3p8x FOREIGN KEY (document_id) REFERENCES application.documents(id)
);


CREATE TABLE application.tm_agency_requests_services (
     agency_request_id int8 NOT NULL,
     support_service_id int8 NOT NULL,
     CONSTRAINT uk2so7rn3o6t8cr0e97enk9i5r9 UNIQUE (agency_request_id, support_service_id),
     CONSTRAINT fkrq5piumn7sm5fvofo2poc2npo FOREIGN KEY (support_service_id) REFERENCES application.lk_support_services(id)
);

INSERT INTO application.lk_tm_agency_request_status
(id, code, name_ar, name_en)
VALUES(1, 'NEW', 'جديد', 'New');

INSERT INTO application.lk_tm_agency_request_status
(id, code, name_ar, name_en)
VALUES(2, 'UNDER_PROCEDURE', 'تحت الاجراء', 'Under Procedure');

INSERT INTO application.lk_tm_agency_request_status
(id, code, name_ar, name_en)
VALUES(3, 'REQUEST_CORRECTION', 'معاد لمقدم الطلب', 'Request Correction');

INSERT INTO application.lk_tm_agency_request_status
(id, code, name_ar, name_en)
VALUES(4, 'REJECTED', 'مرفوض', 'Rejected');

INSERT INTO application.lk_tm_agency_request_status
(id, code, name_ar, name_en)
VALUES(5, 'ACCEPTED', 'مقبول', 'Accepted');

INSERT INTO application.lk_tm_agency_request_status
(id, code, name_ar, name_en)
VALUES(6, 'EXPIRED', 'منتهى', 'Expired');


