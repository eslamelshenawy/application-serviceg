-- First, drop the foreign key constraint
ALTER TABLE application.plant_varieties_details
DROP CONSTRAINT fkr8ce44cy1fkdqp89jdwepn492;

-- Then, drop the column
ALTER TABLE application.plant_varieties_details
DROP COLUMN lk_pv_excellence_traits_id;
