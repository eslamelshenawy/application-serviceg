--liquibase formatted sql
-- changeset application-service:v17_industrial_create_schema_ddl.sql

CREATE TABLE application.industrial_design_details (
	id int8 NOT NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	is_deleted int4 NOT NULL,
	application_id int8 NOT NULL,
	exhibition_date date NULL,
	exhibition_info varchar(2000) NULL,
	explanation_ar varchar(2000) NULL,
	explanation_en varchar(2000) NULL,
	have_exhibition bool NOT NULL,
	request_type varchar(255) NULL,
	secret bool NOT NULL,
	usage_ar varchar(1000) NULL,
	usage_en varchar(1000) NULL,
	CONSTRAINT industrial_design_details_pkey PRIMARY KEY (id)
);


-- application.lk_shapes definition

-- Drop table

-- DROP TABLE application.lk_shapes;

CREATE TABLE application.lk_shapes (
	id int4 NOT NULL,
	code varchar(255) NULL,
	name_ar varchar(255) NULL,
	name_en varchar(255) NULL,
	CONSTRAINT lk_shapes_pkey PRIMARY KEY (id)
);


INSERT INTO application.lk_shapes (id, code, name_ar, name_en) VALUES
                                                                  (1, UPPER('General'), 'منظوري عام', 'General'),
                                                                  (2, UPPER('Front'), 'أمامي', 'Front'),
                                                                  (3, UPPER('Rear'), 'خلفي', 'Rear'),
                                                                  (4, UPPER('Left_Side'), 'جانبي أيسر', 'Left Side'),
                                                                  (5, UPPER('Right_Side'), 'جانبي أيمن', 'Right Side'),
                                                                  (6, UPPER('Upper'), 'علوي', 'Upper'),
                                                                  (7, UPPER('Other'), 'أخرى', 'Other');


alter table application.lk_shapes ALTER COLUMN id TYPE  int8 ;
CREATE TABLE application.design_sample (
                                          id int8 NOT NULL,
                                          created_by_user varchar(255) NULL,
                                          created_date timestamp NULL,
                                          modified_by_user varchar(255) NULL,
                                          modified_date timestamp NULL,
                                          is_deleted int4 NOT NULL,
                                          "name" varchar(255) NULL,
                                          industrial_design_id int8 NULL,
                                          CONSTRAINT design_sample_pkey PRIMARY KEY (id),
                                          CONSTRAINT fkb6gmwhhh2i0fhiklc0qn7yelg FOREIGN KEY (industrial_design_id) REFERENCES application.industrial_design_details(id)
);

CREATE TABLE application.design_sample_drawings (
                                                   id int8 NOT NULL,
                                                   created_by_user varchar(255) NULL,
                                                   created_date timestamp NULL,
                                                   modified_by_user varchar(255) NULL,
                                                   modified_date timestamp NULL,
                                                   is_deleted int4 NOT NULL,
                                                   doc_id int8 NULL,
                                                   main bool NOT NULL,
                                                   design_sample_id int8 NULL,
                                                   shape_id int8 NULL,
                                                   CONSTRAINT design_sample_drawings_pkey PRIMARY KEY (id),
                                                   CONSTRAINT fka9djtuin9agneg9iyhea6ded1 FOREIGN KEY (design_sample_id) REFERENCES application.design_sample(id),
                                                   CONSTRAINT fkasw6atprptyyo9unlt6i6xar FOREIGN KEY (shape_id) REFERENCES application.lk_shapes(id)
);

