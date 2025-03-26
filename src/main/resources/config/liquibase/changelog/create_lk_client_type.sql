CREATE TABLE application.lk_client_type (
                                            id int4 NOT NULL,
                                            created_by_user varchar(255) NULL,
                                            created_date timestamp NULL,
                                            modified_by_user varchar(255) NULL,
                                            modified_date timestamp NULL,
                                            is_deleted int4 NOT NULL,
                                            code varchar(255) NULL,
                                            type_ar varchar(255) NULL,
                                            type_en varchar(255) NULL,
                                            CONSTRAINT lk_client_type_pkey PRIMARY KEY (id)
);