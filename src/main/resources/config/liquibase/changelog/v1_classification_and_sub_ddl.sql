

CREATE TABLE application.classifications (
	id int8 NOT NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	is_deleted int4 NOT NULL,
	code varchar(255) NULL,
	enabled bool NOT NULL,
	name_ar varchar(2000) NOT NULL,
	name_en varchar(2000) NOT NULL,
	nice_version int4 NOT NULL,
	description_ar varchar(2000) NOT NULL,
	description_en varchar(2000) NULL,
	CONSTRAINT classifications_pkey PRIMARY KEY (id)
);

CREATE TABLE application.sub_classifications (
	id int8 NOT NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	is_deleted int4 NOT NULL,
	code varchar(255) NULL,
	enabled bool NOT NULL,
	is_shortcut bool NOT NULL,
	is_visible bool NOT NULL,
	name_ar varchar(2000) NOT NULL,
	name_en varchar(2000) NOT NULL,
	nice_version int4 NOT NULL,
	classification_id int8 NOT NULL,
	description_ar varchar(2000) NULL,
	description_en varchar(2000) NULL,
	basic_number int8 NULL,
	serial_number_ar varchar(255) NULL,
	serial_number_en varchar(255) NULL,
	CONSTRAINT sub_classifications_pkey PRIMARY KEY (id),
	CONSTRAINT fkp7y2kfeengorktpvuoj7agvwr FOREIGN KEY (classification_id) REFERENCES application.classifications(id)
);

CREATE TABLE application.application_sub_classifications (
	id int8 NOT NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	is_deleted int4 NOT NULL,
	sub_classification_id int8 NOT NULL,
	application_id int8 NOT NULL,
	CONSTRAINT application_sub_classifications_pkey PRIMARY KEY (id),
	CONSTRAINT fk7h20d96e5injeijrt92t257vu FOREIGN KEY (sub_classification_id) REFERENCES application.sub_classifications(id),
	CONSTRAINT fkmxmp8muu1g4mkwk42tkx1rtkl FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);