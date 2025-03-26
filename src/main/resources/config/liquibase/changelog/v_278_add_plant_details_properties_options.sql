CREATE TABLE application.plant_details_properties_options (
id int8 NOT NULL PRIMARY KEY,
created_by_user varchar(255) NULL,
created_date timestamp NULL,
modified_by_user varchar(255) NULL,
modified_date timestamp NULL,
is_deleted int4 NOT NULL,
plant_variety_details_id int8,
lk_pv_property_id int8,
lk_pv_property_options_id int8,
note VARCHAR(250),
example VARCHAR(250),
FOREIGN KEY (plant_variety_details_id) REFERENCES application.plant_varieties_details(id),
FOREIGN KEY (lk_pv_property_id) REFERENCES application.lk_pv_property(id),
FOREIGN KEY (lk_pv_property_options_id) REFERENCES application.lk_pv_property_options(id)
);