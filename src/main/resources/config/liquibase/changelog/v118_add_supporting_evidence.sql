-- application.supporting_evidence definition

-- Drop table

DROP TABLE IF EXISTS application.supporting_evidence;

CREATE TABLE application.supporting_evidence (
                                                 id int8 NOT NULL,
                                                 created_date timestamp NULL,
                                                 is_deleted int4 NULL,
                                                 modified_by_user varchar(255) NULL,
                                                 created_by_user varchar(255) NULL,
                                                 modified_date timestamp NULL,
                                                 patent_number varchar NULL,
                                                 address varchar NULL,
                                                 date timestamp NULL,
                                                 classification varchar NULL,
                                                 link varchar NULL,
                                                 document_id int8 NULL,
                                                 is_patent bool NULL,
                                                 substantive_examination_reports_id int8 NULL,
                                                 application_info_id int8 NULL,
                                                 CONSTRAINT supporting_evidence_pk PRIMARY KEY (id)
);



