CREATE TABLE application.support_service_reviews (
 id int8 NOT NULL,
 created_by_user varchar(255) NULL,
 created_date timestamp NULL,
 modified_by_user varchar(255) NULL,
 modified_date timestamp NULL,
 is_deleted int4 NOT NULL,
 review text NULL,
 review_status varchar(255) NULL,
 application_support_services_type_id int8 NULL,
 CONSTRAINT support_service_reviews_pkey PRIMARY KEY (id),
 CONSTRAINT fkqjhls39qsfk1jdkvnfxw9hxjr FOREIGN KEY (application_support_services_type_id) REFERENCES application.application_support_services_type(id)
);