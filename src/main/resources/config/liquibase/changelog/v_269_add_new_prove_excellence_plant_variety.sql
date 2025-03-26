CREATE TABLE application.pv_prove_excellence (
                                                 id int8 NOT NULL,
                                                 created_by_user varchar(255) NULL,
                                                 created_date timestamp NULL,
                                                 modified_by_user varchar(255) NULL,
                                                 modified_date timestamp NULL,
                                                 is_deleted int4 NOT NULL,
                                                 plant_name_similar_your_plant varchar(255) NULL,
                                                 attribute_plant_description varchar(255) NULL,
                                                 lk_pv_excellence_traits_id int8 NULL,
                                                 lk_pv_excellence_traits_features_id int8 NULL,
                                                 plant_variety_details_id int8 NULL,
                                                 CONSTRAINT pv_prove_excellence_pkey PRIMARY KEY (id),
                                                 CONSTRAINT fkghnfr8p0u8yaxx6xlrb6jb8rk FOREIGN KEY (plant_variety_details_id) REFERENCES application.plant_varieties_details(id),
                                                 CONSTRAINT fkgxojc0uwmi9x2f0c2is0l7g5f FOREIGN KEY (lk_pv_excellence_traits_id) REFERENCES application.lk_pv_excellence_traits(id),
                                                 CONSTRAINT fkp8giq5xnoyklwyd5ffqi1qdal FOREIGN KEY (lk_pv_excellence_traits_features_id) REFERENCES application.lk_pv_excellence_traits_features(id)
);
