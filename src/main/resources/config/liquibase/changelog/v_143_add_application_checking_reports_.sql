-- application.application_checking_reports definition

-- Drop table

-- DROP TABLE application.application_checking_reports;
DROP TABLE IF exists  application.application_checking_reports CASCADE;
CREATE TABLE application.application_checking_reports (
                                                          id int8 NOT NULL,
                                                          created_by_user varchar(255) NULL,
                                                          created_date timestamp NULL,
                                                          modified_by_user varchar(255) NULL,
                                                          modified_date timestamp NULL,
                                                          is_deleted int4 NOT NULL,
                                                          document_id int8 NULL,
                                                          report_type varchar(255) NULL,
                                                          application_id int8 NOT NULL,
                                                          CONSTRAINT application_checking_reports_pkey PRIMARY KEY (id)
);


-- application.application_checking_reports foreign keys

ALTER TABLE application.application_checking_reports ADD CONSTRAINT fkqf7mb7nid5hf5joc694xpfahp FOREIGN KEY (application_id) REFERENCES application.applications_info(id);