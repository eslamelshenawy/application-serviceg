ALTER TABLE application.plant_varieties_details
    ADD COLUMN IF NOT EXISTS item_produced_by VARCHAR(250);