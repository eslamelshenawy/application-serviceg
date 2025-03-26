
ALTER TABLE IF EXISTS application.lk_pv_excellence_traits_features
RENAME TO lk_pv_property_options;


ALTER TABLE IF EXISTS application.lk_pv_property_options
RENAME COLUMN  pv_excellence_traits_id TO lk_pv_property_id;





