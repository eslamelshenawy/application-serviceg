CREATE TABLE application.lk_vegetarian_types (
                                             id int8 NOT NULL,
                                             created_by_user varchar(255) NULL,
                                             created_date timestamp NULL,
                                             modified_by_user varchar(255) NULL,
                                             modified_date timestamp NULL,
                                             is_deleted int4 NOT NULL,
                                             code varchar(255) NULL,
                                             name_ar varchar(255) NULL,
                                             name_en varchar(255) NULL,
                                             scientific_name varchar(255) NULL,
                                             protection_period int8,
                                             marketing_period_in_ksa int8,
                                             marketing_period_out_ksa int8,
                                             code_number int8,
                                             is_active bool,
                                             CONSTRAINT lk_vegetarian_types_pkey PRIMARY KEY (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE application.lk_pv_excellence_traits (
                                                          id int8 NOT NULL,
                                                          created_by_user varchar(255) NULL,
                                                          created_date timestamp NULL,
                                                          modified_by_user varchar(255) NULL,
                                                          modified_date timestamp NULL,
                                                          is_deleted int4 NOT NULL,
                                                          code varchar(255) NULL,
                                                          name_ar varchar(255) NULL,
                                                          name_en varchar(255) NULL,
                                                          lk_vegetarian_types_id int8, -- Corrected column name
                                                          CONSTRAINT lk_pv_excellence_traits_pkey PRIMARY KEY (id),
                                                          CONSTRAINT lk_pv_excellence_traits_fk FOREIGN KEY (lk_vegetarian_types_id) REFERENCES application.lk_vegetarian_types(id)
);