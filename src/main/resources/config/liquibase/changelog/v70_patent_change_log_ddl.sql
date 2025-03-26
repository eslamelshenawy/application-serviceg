--liquibase formatted sql
-- changeset application-service:v70_patent_change_log_ddl.sql

DROP TABLE if exists application.patent_attribute_change_logs;

CREATE TABLE application.patent_attribute_change_logs (
          id int8 NOT NULL,
          created_by_user varchar(255) NULL,
          created_date timestamp NULL,
          modified_by_user varchar(255) NULL,
          modified_date timestamp NULL,
          is_deleted int4 NOT NULL,
          attribute_name varchar(255) NULL,
          attribute_value text NULL,
          task_definition_key varchar(255) NULL,
          task_id varchar(255) NULL,
          "version" int4 NULL DEFAULT 1,
          patent_details_id int8 NULL,
          CONSTRAINT patent_attribute_change_logs_pkey PRIMARY KEY (id),
          CONSTRAINT fk7cve3jcakb7sc4mg4vxcbnudx FOREIGN KEY (patent_details_id) REFERENCES application.patent_details(id)
);