ALTER TABLE application.plant_varieties_details
    ADD COLUMN IF NOT EXISTS hybridization_father_name VARCHAR(250),
    ADD COLUMN IF NOT EXISTS hybridization_mother_name VARCHAR(250),
    ADD COLUMN IF NOT EXISTS reproduction_method_clarify VARCHAR(250),
    ADD COLUMN IF NOT EXISTS vegetarian_type_use_clarify VARCHAR(250);