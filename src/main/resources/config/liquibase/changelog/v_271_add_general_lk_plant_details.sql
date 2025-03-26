-- Add columns if they do not exist
ALTER TABLE application.plant_varieties_details
    ADD COLUMN IF NOT EXISTS hybridization_type_id int4,
    ADD COLUMN IF NOT EXISTS reproduction_method_id int4,
    ADD COLUMN IF NOT EXISTS vegetarian_type_use_id int4,
    ADD COLUMN IF NOT EXISTS illness_result_id int4;

-- Add foreign key constraints if they do not exist
ALTER TABLE application.plant_varieties_details
    ADD CONSTRAINT fk_hybridization_type FOREIGN KEY (hybridization_type_id) REFERENCES application.lk_plant_details(id);

ALTER TABLE application.plant_varieties_details
    ADD CONSTRAINT fk_reproduction_method FOREIGN KEY (reproduction_method_id) REFERENCES application.lk_plant_details(id);

ALTER TABLE application.plant_varieties_details
    ADD CONSTRAINT fk_vegetarian_type_use FOREIGN KEY (vegetarian_type_use_id) REFERENCES application.lk_plant_details(id);

ALTER TABLE application.plant_varieties_details
    ADD CONSTRAINT fk_illness_result FOREIGN KEY (illness_result_id) REFERENCES application.lk_plant_details(id);
