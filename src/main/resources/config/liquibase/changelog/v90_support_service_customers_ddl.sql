CREATE TABLE application.support_service_customer (
          id int8 NOT NULL,
          created_by_user varchar(255) NULL,
          created_date timestamp NULL,
          modified_by_user varchar(255) NULL,
          modified_date timestamp NULL,
          is_deleted int4 NOT NULL,
          customer_id int8 NULL,
          customer_code varchar(255) NULL,
          agency_request_id int8 NULL,
          application_customer_type varchar(255) NULL,
          customer_application_access_level varchar(255) NULL,
          support_service_id int8 NULL,
          CONSTRAINT support_service_customer_pk PRIMARY KEY (id),
          CONSTRAINT support_service_customer_uniquek UNIQUE (support_service_id, customer_id),
          CONSTRAINT support_service_customer_support_service_id_fk FOREIGN KEY (support_service_id) REFERENCES application.application_support_services_type(id),
          CONSTRAINT support_service_customer_agency_id_fk FOREIGN KEY (agency_request_id) REFERENCES application.trademark_agency_requests(id)
);