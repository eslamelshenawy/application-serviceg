CREATE TABLE application.application_drawing (
     id int8 NOT NULL,
     created_by_user varchar(255) NULL,
     created_date timestamp NULL,
     modified_by_user varchar(255) NULL,
     modified_date timestamp NULL,
     is_deleted int4 NOT NULL,
     is_default bool NOT NULL,
     title varchar(255) NULL,
     application_id int8 NULL,
     document_id int8 NULL,
     CONSTRAINT application_drawing_pkey PRIMARY KEY (id),
     CONSTRAINT uk3oofe3x68k0239rchyeg5xn7d UNIQUE (application_id, document_id),
     CONSTRAINT fkis0869v0tngd4lqwi4cn56kxw FOREIGN KEY (document_id) REFERENCES application.documents(id),
     CONSTRAINT fkmxruxw33hxec1cnrwa2jwuilk FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);