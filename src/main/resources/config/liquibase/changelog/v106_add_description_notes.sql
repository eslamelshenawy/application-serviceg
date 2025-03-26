ALTER TABLE application.application_section_notes ADD id int8 NULL;
ALTER TABLE application.application_section_notes ADD description text NULL;
ALTER TABLE application.application_section_notes ADD is_deleted int4 NULL;
ALTER TABLE application.application_section_notes ADD created_date timestamp NULL;
ALTER TABLE application.application_section_notes ADD created_by_user  varchar(255) NULL;
ALTER TABLE application.application_section_notes ADD modified_ate timestamp NULL;
ALTER TABLE application.application_section_notes ADD modified_by_user  varchar(255) NULL;


CREATE TABLE application.supporting_evidence (
                                                 id int8 NULL,
                                                 created_date timestamp NULL,
                                                 is_deleted int4 NULL,
                                                 modified_by_user varchar(255) NULL,
                                                 created_by_user varchar(255) NULL,
                                                 modified_date timestamp NULL,
                                                 substantive_examination_reports_id int8 null,
                                                 patent_number varchar NULL,
                                                 address varchar NULL,
                                                 "date" timestamp NULL,
                                                 classification varchar NULL,
                                                 link varchar NULL,
                                                 document_id int8 null,
                                                 application_info_id int8 null,
                                                 is_patent boolean NULL
);

ALTER TABLE application.supporting_evidence ADD CONSTRAINT supporting_evidence_pk PRIMARY KEY (id);



