CREATE TABLE application.dus_testing_documents (
                                                   id int8 NOT NULL,
                                                   created_by_user varchar(255) NULL,
                                                   created_date timestamp NULL,
                                                   modified_by_user varchar(255) NULL,
                                                   modified_date timestamp NULL,
                                                   is_deleted int4 NOT NULL,
                                                   country_id int8 NULL,
                                                   application_id int8 NULL,
                                                   document_id int8 NULL,
                                                   CONSTRAINT dus_testing_documents_pkey PRIMARY KEY (id),
                                                   CONSTRAINT fkhd0otchiwi08455s2r8vw6gg9 FOREIGN KEY (document_id) REFERENCES application.documents(id),
                                                   CONSTRAINT fkl7rynlxisu43vy11rb2u7c2nu FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);