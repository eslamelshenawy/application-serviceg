--liquibase formatted sql
-- changeset application-service:documents_template.sql

CREATE TABLE application.documents_template (
                                                id int8 NOT NULL,
                                                created_by_user varchar(255) NULL,
                                                created_date timestamp NULL,
                                                modified_by_user varchar(255) NULL,
                                                modified_date timestamp NULL,
                                                is_deleted int4 NOT NULL,
                                                file_name varchar(255) NULL,
                                                label_name_ar varchar(255) NULL,
                                                label_name_en varchar(255) NULL,
                                                nexuo_id varchar(255) NULL,
                                                uploaded_date timestamp NULL,
                                                category_id int8 NULL,
                                                lk_nexuo_user_id int8 NULL,
                                                CONSTRAINT documents_template_pkey PRIMARY KEY (id),
                                                CONSTRAINT fkdbhxc0ny0n799jgps49bl2cuv FOREIGN KEY (lk_nexuo_user_id) REFERENCES application.lk_nexuo_user(id),
                                                CONSTRAINT fkjkd1pmwnq53inp7pjw6fw1sf FOREIGN KEY (category_id) REFERENCES application.lk_application_category(id)
);