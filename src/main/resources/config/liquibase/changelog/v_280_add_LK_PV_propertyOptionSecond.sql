ALTER TABLE application.pv_prove_excellence ADD COLUMN IF NOT EXISTS lk_pv_property_options_second_id int8 NULL;

ALTER TABLE application.pv_prove_excellence
    ADD CONSTRAINT fk_lkpv_property_options_second FOREIGN KEY (lk_pv_property_options_second_id) REFERENCES application.lk_pv_property_options (id);
