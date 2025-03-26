--liquibase formatted sql
-- changeset application-service:lk_notes_DDL.sql

CREATE TABLE IF NOT EXISTS application.lk_notes (
                                      id int8 NOT NULL,
                                      created_by_user varchar(255) NULL,
                                      created_date timestamp NULL,
                                      modified_by_user varchar(255) NULL,
                                      modified_date timestamp NULL,
                                      is_deleted int4 NOT NULL,
                                      code varchar(255) NULL,
                                      description_ar varchar(255) NULL,
                                      description_en varchar(255) NULL,
                                      enabled bool NOT NULL,
                                      name_ar varchar(255) NULL,
                                      name_en varchar(255) NULL,
                                      notes_ar varchar(255) NULL,
                                      notes_en varchar(255) NULL,
                                      category_id int8 NULL,
                                      section_id int4 NULL,
                                      step_id int4 NULL,
                                      CONSTRAINT lk_notes_pkey PRIMARY KEY (id),
                                      CONSTRAINT fk8hwfp56gyol8ry8ib93mocmwb FOREIGN KEY (step_id) REFERENCES application.lk_steps(id),
                                      CONSTRAINT fklg0xl6aylv7ga920bxx8u6f4t FOREIGN KEY (section_id) REFERENCES application.lk_sections(id),
                                      CONSTRAINT fknjfaql53nwbke31ft3mbf6ae4 FOREIGN KEY (category_id) REFERENCES application.lk_application_category(id)
);