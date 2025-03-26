--liquibase formatted sql
-- changeset application-service:application_notes_DDL.sql

CREATE TABLE IF NOT EXISTS application.application_notes (
                                               id int8 NOT NULL,
                                               created_by_user varchar(255) NULL,
                                               created_date timestamp NULL,
                                               modified_by_user varchar(255) NULL,
                                               modified_date timestamp NULL,
                                               is_deleted int4 NOT NULL,
                                               description varchar(255) NULL,
                                               is_done bool NOT NULL,
                                               application_id int8 NOT NULL,
                                               section_id int4 NULL,
                                               step_id int4 NULL,
                                               CONSTRAINT application_notes_pkey PRIMARY KEY (id),
                                               CONSTRAINT fk2e62gwmuktppxbvvh5cja3s7g FOREIGN KEY (step_id) REFERENCES application.lk_steps(id),
                                               CONSTRAINT fkhm6t41bh36k6cbtt126em9bhp FOREIGN KEY (section_id) REFERENCES application.lk_sections(id),
                                               CONSTRAINT fkq2f6xn8fb0ogdm1hnpcx606ap FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);