-- Add columns to the table if they don't already exist
-- Add explain_difference column if it doesn't already exist
ALTER TABLE application.pv_prove_excellence
    ADD COLUMN explain_difference varchar(255) NULL;

-- Add description_trait_similar_category column if it doesn't already exist
ALTER TABLE application.pv_prove_excellence
    ADD COLUMN description_trait_similar_category varchar(255) NULL;

-- Rename the constraints
ALTER TABLE application.pv_prove_excellence
    RENAME CONSTRAINT fkgxojc0uwmi9x2f0c2is0l7g5f TO lk_pv_property_id;
ALTER TABLE application.pv_prove_excellence
    RENAME CONSTRAINT fkp8giq5xnoyklwyd5ffqi1qdal TO lk_pv_property_options_id;

ALTER TABLE application.pv_prove_excellence
RENAME COLUMN lk_pv_excellence_traits_id to lk_pv_property_id;
-- Then, rename the column
ALTER TABLE application.pv_prove_excellence
RENAME COLUMN lk_pv_excellence_traits_features_id to lk_pv_property_options_id;
-- Then, Add the columns






