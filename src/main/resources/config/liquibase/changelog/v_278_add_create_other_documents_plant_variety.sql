CREATE TABLE application.other_plant_variety_documents (
                                                           id int8 NOT NULL,
                                                           created_by_user varchar(255) NULL,
                                                           created_date timestamp NULL,
                                                           modified_by_user varchar(255) NULL,
                                                           modified_date timestamp NULL,
                                                           is_deleted int4 NOT NULL,
                                                           file_name varchar(255) NULL,
                                                           application_id int8 NULL,
                                                           document_id int8 NULL,
                                                           CONSTRAINT other_plant_variety_documents_pkey PRIMARY KEY (id),
                                                           CONSTRAINT fk6su3b9gb3axq0t009f6uf5b2b FOREIGN KEY (document_id) REFERENCES application.documents(id),
                                                           CONSTRAINT fksowf7nr2e594kxx3wlmlgdtbu FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);