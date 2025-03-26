CREATE TABLE application.similar_trademark (
                                               id int8 NOT NULL,
                                               created_by_user varchar(255) NULL,
                                               created_date timestamp NULL,
                                               modified_by_user varchar(255) NULL,
                                               modified_date timestamp NULL,
                                               is_deleted int4 NOT NULL,
                                               image_link varchar(255) NULL,
                                               preview_link varchar(255) NULL,
                                               task_definition_key varchar(255) NULL,
                                               task_instance_id varchar(255) NULL,
                                               trademark_number varchar(255) NULL,
                                               application_id int8 NULL,
                                               CONSTRAINT similar_trademark_pkey PRIMARY KEY (id)
);
ALTER TABLE application.similar_trademark ADD CONSTRAINT fkdt0uyaam3s2leyon52on5aflm FOREIGN KEY (application_id) REFERENCES application.applications_info(id);