CREATE TABLE application.application_priority_request (
    id int8 NOT NULL,
    modify_type varchar(4) NOT NULL,
    reason varchar(255) NOT NULL,
    document_id int8,
    CONSTRAINT application_priority_request_pkey PRIMARY KEY (id),
    CONSTRAINT application_priority_request_fk01 FOREIGN KEY (id) REFERENCES application.application_support_services_type(id)
);