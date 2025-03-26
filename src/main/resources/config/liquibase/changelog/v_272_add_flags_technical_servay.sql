ALTER TABLE application.plant_varieties_details
    ADD COLUMN IF NOT EXISTS hybridization_type_flag bool,
    ADD COLUMN IF NOT EXISTS leap_flag bool,
    ADD COLUMN IF NOT EXISTS discovery_flag bool,
    ADD COLUMN IF NOT EXISTS other_flag bool;