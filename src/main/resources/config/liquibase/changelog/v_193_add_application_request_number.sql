
-- create a new sequence
CREATE sequence if not exists application.applications_info_request_number_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- update the sequence
SELECT setval('application.applications_info_request_number_seq',(SELECT nextval('id_seq') + 1000));

-- add a new column
ALTER TABLE application.applications_info  ADD column if not exists application_request_number VARCHAR(255) DEFAULT NULL;

-- add unique constraint
ALTER TABLE application.applications_info ADD CONSTRAINT request_number_and_category_un UNIQUE (application_request_number, lk_category_id);


-- update old trademarks
update application.applications_info set application_request_number = 'SA-'||id where lk_category_id = 5;

