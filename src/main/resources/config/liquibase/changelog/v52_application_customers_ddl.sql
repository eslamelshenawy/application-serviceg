--liquibase formatted sql
-- changeset application-service:v52_application_customers_ddl.sql

CREATE TABLE application.application_customers (
    id int8 NOT NULL,
    created_by_user varchar(255) NULL,
    created_date timestamp NULL,
    modified_by_user varchar(255) NULL,
    modified_date timestamp NULL,
    is_deleted int4 NOT NULL,
    customer_code varchar(255) NULL,
    customer_id int8 NULL,
    application_customer_type varchar(255) NULL,
    application_id int8 NULL,
    CONSTRAINT application_customers_pkey PRIMARY KEY (id),
    CONSTRAINT ukon7jx9r48ujl6s1hb04mcmcuk UNIQUE (application_id, customer_id, customer_code),
    CONSTRAINT fklbr1fcg0qpy7ms14mka1ouwgd FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);