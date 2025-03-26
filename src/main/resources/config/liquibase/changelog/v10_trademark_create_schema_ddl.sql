

-- Drop table

-- DROP TABLE application.lk_mark_types;

CREATE TABLE application.lk_mark_types (
	id int4 NOT NULL,
	code varchar(255) NOT NULL,
	name_ar varchar(255) NOT NULL,
	name_en varchar(255) NOT NULL,
	CONSTRAINT lk_mark_types_pkey PRIMARY KEY (id)
);


-- application.lk_tag_languages definition

-- Drop table

-- DROP TABLE application.lk_tag_languages;

CREATE TABLE application.lk_tag_languages (
	id int4 NOT NULL,
	code varchar(255) NOT NULL,
	name_ar varchar(255) NOT NULL,
	name_en varchar(255) NOT NULL,
	CONSTRAINT lk_tag_languages_pkey PRIMARY KEY (id)
);


-- application.lk_tag_type_desc definition

-- Drop table

-- DROP TABLE application.lk_tag_type_desc;

CREATE TABLE application.lk_tag_type_desc (
	id int4 NOT NULL,
	code varchar(255) NOT NULL,
	name_ar varchar(255) NOT NULL,
	name_en varchar(255) NOT NULL,
	CONSTRAINT lk_tag_type_desc_pkey PRIMARY KEY (id)
);



-- application.trademark_details definition

-- Drop table

-- DROP TABLE application.trademark_details;

CREATE TABLE application.trademark_details (
	id int8 NOT NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	is_deleted int4 NOT NULL,
	application_id int8 NOT NULL,
	exhibition_date date NULL,
	exhibition_info varchar(255) NULL,
	exposed_date date NULL,
	have_exhibition bool NOT NULL,
	is_exposed_public bool NOT NULL,
	mark_claiming_color varchar(255) NULL,
	mark_description varchar(255) NULL,
	word_mark varchar(255) NULL,
	mark_type_id int4 NOT NULL,
	tag_language_id int4 NOT NULL,
	tag_type_desc_id int4 NOT NULL,
	CONSTRAINT trademark_details_pkey PRIMARY KEY (id),
	CONSTRAINT fkb0wlt4tg83m012wvyen433bid FOREIGN KEY (mark_type_id) REFERENCES application.lk_mark_types(id),
	CONSTRAINT fkdmfn8akig9r40i6922lc00wi4 FOREIGN KEY (tag_language_id) REFERENCES application.lk_tag_languages(id),
	CONSTRAINT fksqi9tmdk8nl9a4bk05lxi12ln FOREIGN KEY (tag_type_desc_id) REFERENCES application.lk_tag_type_desc(id)
);
