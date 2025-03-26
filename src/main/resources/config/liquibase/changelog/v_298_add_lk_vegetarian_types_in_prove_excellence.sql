ALTER TABLE application.pv_prove_excellence ADD COLUMN if not exists lk_vegetarian_types_id BIGINT;
ALTER TABLE application.pv_prove_excellence ADD CONSTRAINT fk_vegetarian_type FOREIGN KEY (lk_vegetarian_types_id) REFERENCES application.lk_vegetarian_types(id);

