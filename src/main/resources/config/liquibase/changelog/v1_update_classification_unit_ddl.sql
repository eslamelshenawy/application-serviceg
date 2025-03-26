
ALTER TABLE application.classifications DROP CONSTRAINT fk5m01qd4e5hef4ia106eqr5anc;
ALTER TABLE application.classifications DROP COLUMN unit_id;
DROP TABLE application.lk_classification_units CASCADE;

CREATE TABLE application.lk_classification_units (
	id int8 NOT NULL,
	code varchar(255) NULL,
	name_ar varchar(255) NULL,
	name_en varchar(255) NULL,
	category_id int8 NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	is_deleted int4 NOT NULL,
	CONSTRAINT lk_classification_units_pkey PRIMARY KEY (id)
);

ALTER TABLE application.lk_classification_units ADD CONSTRAINT fk5mtnqx01vab_unit FOREIGN KEY (category_id) REFERENCES application.lk_application_category(id);

alter table application.classifications add column unit_id int8;

alter table application.classifications add constraint FK5m01qd4e5hef4ia106eqr5anc foreign key (unit_id) references application.lk_classification_units;
