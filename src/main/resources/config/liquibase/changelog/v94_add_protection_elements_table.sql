

CREATE TABLE application.protection_elements (
                                                id int8 NOT NULL,
                                                created_by_user varchar(255) NULL,
                                                created_date timestamp NULL,
                                                modified_by_user varchar(255) NULL,
                                                modified_date timestamp NULL,
                                                is_deleted int4 NOT NULL,
                                                description varchar(255) NULL,
                                                application_id int8 NULL,
                                                parent_id int8 NULL,
                                                CONSTRAINT protection_elements_pkey PRIMARY KEY (id)
);



ALTER TABLE application.protection_elements ADD CONSTRAINT fk7pwcet3o4g8vvu7l0x5o19ber FOREIGN KEY (parent_id) REFERENCES application.protection_elements(id);
ALTER TABLE application.protection_elements ADD CONSTRAINT fkkxa4dr7vq27u64og02d5mkxj7 FOREIGN KEY (application_id) REFERENCES application.applications_info(id);