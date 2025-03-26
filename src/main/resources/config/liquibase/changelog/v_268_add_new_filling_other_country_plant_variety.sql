
CREATE TABLE application.filling_request_other_country (
                                                           id int8 NOT NULL,
                                                           created_by_user varchar(255) NULL,
                                                           created_date timestamp NULL,
                                                           modified_by_user varchar(255) NULL,
                                                           modified_date timestamp NULL,
                                                           is_deleted int4 NOT NULL,
                                                           classification varchar(255) NULL,
                                                           country_id int8 NULL,
                                                           filling_date_request timestamp NULL,
                                                           plant_variety_details_id int8 NULL,
                                                           registration_request_other_country_number varchar(255) NULL,
                                                           CONSTRAINT filling_request_other_country_pkey PRIMARY KEY (id)
);