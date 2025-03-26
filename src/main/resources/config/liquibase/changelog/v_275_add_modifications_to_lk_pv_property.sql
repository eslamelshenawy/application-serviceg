ALTER TABLE IF EXISTS application.lk_pv_traits_groups DROP CONSTRAINT IF EXISTS lk_pv_traits_groups_lk_vegetarian_type_id_fkey ;
ALTER TABLE IF EXISTS application.lk_pv_traits_groups_features DROP CONSTRAINT IF EXISTS pv_traits_groups_fk ;
ALTER TABLE IF EXISTS application.lk_pv_resistance_diseases_groups_features DROP CONSTRAINT IF EXISTS pv_resistance_diseases_groups_fk;
ALTER TABLE IF EXISTS application.lk_pv_resistance_diseases_groups DROP CONSTRAINT IF EXISTS lk_pv_resistance_diseases_groups_lk_vegetarian_type_id_fkey;


DROP TABLE IF EXISTS application.lk_pv_traits_groups;
DROP TABLE IF EXISTS application.lk_pv_traits_groups_features;
DROP TABLE IF EXISTS application.lk_pv_resistance_diseases_groups;
DROP TABLE IF EXISTS application.lk_pv_resistance_diseases_groups_features;

ALTER TABLE IF EXISTS application.lk_pv_excellence_traits
RENAME TO lk_pv_property;

ALTER TABLE application.lk_pv_property ADD COLUMN excellence VARCHAR(250);
ALTER TABLE application.lk_pv_property ADD COLUMN type VARCHAR(250);
