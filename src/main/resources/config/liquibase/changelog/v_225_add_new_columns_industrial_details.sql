ALTER TABLE application.industrial_design_details
    ADD COLUMN have_revealed_to_public BOOLEAN,
    ADD COLUMN via_mySelf BOOLEAN,
    ADD COLUMN detection_date DATE;

ALTER TABLE application.industrial_design_details
DROP COLUMN IF exists secret ;


