CREATE TABLE application.application_legal_documents (
	id int8 NOT NULL,
	file_name varchar(255) NULL,
    application_id int8 NULL,
    document_id int8 NULL,
    is_deleted int4 NOT NULL,
	created_by_user varchar(255) NULL,
	created_date timestamp NULL,
	modified_by_user varchar(255) NULL,
	modified_date timestamp NULL,
	CONSTRAINT application_legal_documents_pkey PRIMARY KEY (id),
	CONSTRAINT fklrxyvq6umagkmg76fbkndtfib FOREIGN KEY (application_id) REFERENCES application.applications_info(id),
	CONSTRAINT fkqyfm7k1m8vbsgwu56c9hs2blw FOREIGN KEY (document_id) REFERENCES application.documents(id)
);