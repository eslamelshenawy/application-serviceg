-- Add new columns if they don't already exist
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS pph_pct_examination BOOL DEFAULT false;
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS fast_track_type VARCHAR(250);
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS pct_number VARCHAR(250);
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS matching_explanation_protection_elements BOOL DEFAULT false;
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS all_last_protection_elements_similar_office BOOL DEFAULT false;

-- Add columns with the text data type
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS demand_protection_elements TEXT;
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS last_demand_protection_elements TEXT;
ALTER TABLE application.application_accelerated ADD COLUMN IF NOT EXISTS matching_explanation TEXT;
