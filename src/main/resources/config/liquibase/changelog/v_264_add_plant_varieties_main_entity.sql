CREATE TABLE application.plant_varieties_details (
                                             id int8 NOT NULL,
                                             created_by_user varchar(255) NULL,
                                             created_date timestamp NULL,
                                             modified_by_user varchar(255) NULL,
                                             modified_date timestamp NULL,
                                             is_deleted int4 NOT NULL,
                                             additional_feature_differentiate_variety bool NOT NULL,
                                             additional_feature_differentiate_variety_note varchar(255) NULL,
                                             chemical_edit bool NOT NULL,
                                             chemical_edit_note varchar(255) NULL,
                                             commercial_name_ar varchar(255) NULL,
                                             commercial_name_en varchar(255) NULL,
                                             description_ar varchar(255) NULL,
                                             description_en varchar(255) NULL,
                                             description_variety_development varchar(255) NULL,
                                             detailed_survey varchar(255) NULL,
                                             development_genetic_engineering bool NOT NULL,
                                             development_genetic_engineering_note varchar(255) NULL,
                                             discovery_date timestamp NULL,
                                             first_filling_date_in_ksa timestamp NULL,
                                             first_filling_date_out_ksa timestamp NULL,
                                             margeking_in_ksa bool NOT NULL,
                                             margeking_in_ksa_note varchar(255) NULL,
                                             margeking_out_ksa bool NOT NULL,
                                             margeking_out_ksa_note varchar(255) NULL,
                                             microbiology bool NOT NULL,
                                             microbiology_note varchar(255) NULL,
                                             other_description varchar(255) NULL,
                                             other_details varchar(255) NULL,
                                             other_factors bool NOT NULL,
                                             other_factors_note varchar(255) NULL,
                                             parent_name_description varchar(255) NULL,
                                             plant_conditional_testing bool NOT NULL,
                                             plant_conditional_testing_note varchar(255) NULL,
                                             plant_homogeneous bool NOT NULL,
                                             plant_homogeneous_note varchar(255) NULL,
                                             plant_origination varchar(255) NULL,
                                             protection_other_diseases varchar(255) NULL,
                                             specific_plant bool NOT NULL,
                                             specific_plant_note varchar(255) NULL,
                                             tissue_culture bool NOT NULL,
                                             tissue_culture_note varchar(255) NULL,
                                             variables_during_reproductive bool NOT NULL,
                                             variables_during_reproductive_note varchar(255) NULL,
                                             application_id int8 NULL,
                                             lk_pv_excellence_traits_id int8 NULL,
                                             lk_vegetarian_types_id int8 NULL,
                                             CONSTRAINT plant_varieties_details_pkey PRIMARY KEY (id),
                                             CONSTRAINT fkin76kd0bmfq4yvoq4g70mvlm9 FOREIGN KEY (application_id) REFERENCES application.applications_info(id),
                                             CONSTRAINT fkopafbms1ple7rxq2f597356kd FOREIGN KEY (lk_vegetarian_types_id) REFERENCES application.lk_vegetarian_types(id),
                                             CONSTRAINT fkr8ce44cy1fkdqp89jdwepn492 FOREIGN KEY (lk_pv_excellence_traits_id) REFERENCES application.lk_pv_excellence_traits(id)
);