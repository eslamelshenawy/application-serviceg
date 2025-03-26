CREATE TABLE application.integrated_circuits (
	id int8 NOT NULL,
	application_id int8 NULL,
	design_description varchar(255) NULL,
	design_date timestamp NULL,
	is_commercial_exploited bool NULL,
	commercial_exploitation_date timestamp NULL,
	country_id int8 NULL,
	commercial_exploitation_document_id int8 NULL,
	notify_checker bool NULL,
	is_deleted int4 NOT NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	CONSTRAINT integrated_circuits_pkey PRIMARY KEY (id),
	CONSTRAINT fkikg63ccnovjee4x1774sfdcpq FOREIGN KEY (application_id) REFERENCES application.applications_info(id),
	CONSTRAINT fkshdcq4olhohvv5dejmm0cmmvi FOREIGN KEY (commercial_exploitation_document_id) REFERENCES application.documents(id)
);